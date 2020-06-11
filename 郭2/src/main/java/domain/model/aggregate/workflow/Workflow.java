package domain.model.aggregate.workflow;

import domain.model.aggregate.AggregateRoot;
import domain.model.aggregate.workflow.event.*;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Workflow extends AggregateRoot {
    private String boardId;
    private String workflowId;
    private String workflowName;
    private List<Lane> laneList;

    public Workflow(String workflowName, String boardId) {
        laneList = new ArrayList<Lane>();
        this.boardId = boardId;
        this.workflowId = UUID.randomUUID().toString();
        this.workflowName = workflowName;
        addDomainEvent(new WorkflowCreated(boardId, this.workflowId));
    }

    public Workflow(String workflowName) {
        laneList = new ArrayList<Lane>();
        this.workflowId = UUID.randomUUID().toString();
        this.workflowName = workflowName;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setLaneList(List<Lane> laneList) {
        this.laneList = laneList;
    }

    public List<Lane> getLaneList() {
        return laneList;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getBoardId() {
        return boardId;
    }

    public Lane createSwimlane(String swimlaneName) {
        Lane swimlane = new Swimlane(swimlaneName, workflowId);
        laneList.add(swimlane);
        addDomainEvent(new SwimlaneCreated(boardId, workflowId, swimlane.getLaneId()));
        return swimlane;
    }

    public Lane createStage(String stageName) {
        Lane stage = new Stage(stageName, workflowId);
        laneList.add(stage);
        addDomainEvent(new StageCreated(boardId, workflowId, stage.getLaneId()));
        return stage;
    }

    public void addCardInLane(String laneId, String cardId) {
        Lane toLane = getLaneById(laneId);
        if (null == toLane)
            throw new RuntimeException("Cannot commit a card to a non-existing land '" + laneId + "'");
        toLane.addCardId(cardId);
        addDomainEvent(new CardCommitted(workflowId, laneId, cardId));
    }

    public void deleteCardFromLane(String laneId, String cardId) {
        Lane lane = getLaneById(laneId);
        if (null == lane)
            throw new RuntimeException("Cannot uncommit a card from a non-existing land '" + laneId + "'");
        lane.deleteCard(cardId);
        addDomainEvent(new CardUnCommitted(workflowId, laneId, cardId));
    }

    public Lane getLaneById(String laneId) {
        for (Lane each : laneList) {
            if (each.getLaneId().equalsIgnoreCase(laneId)) {
                return each;
            }
        }
        throw new RuntimeException("Stage is not found,id=" + laneId);
    }

    public void moveCard(String cardId, String fromLaneId, String toLaneId) {
        deleteCardFromLane(fromLaneId, cardId);
        addCardInLane(toLaneId, cardId);
    }

}