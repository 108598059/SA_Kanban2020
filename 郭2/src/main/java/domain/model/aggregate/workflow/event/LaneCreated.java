package domain.model.aggregate.workflow.event;

import domain.model.DomainEvent;
import domain.model.aggregate.workflow.Lane;
import domain.model.common.DateProvider;

import java.util.Date;

abstract public class LaneCreated implements DomainEvent {
    private String boardId;
    private String workflowId;
    private String laneId;
    private Date occurredOn;

    public LaneCreated(String boardId, String workflowId, String laneId) {
        this.boardId = boardId;
        this.laneId = laneId;
        this.workflowId = workflowId;
        this.occurredOn = DateProvider.now();
    }

    public String getBoardId() {
        return boardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getLaneId() {
        return laneId;
    }

    @Override
    public Date getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String detail() {
        return "LaneCreated " + "workflowId = " + workflowId;
    }
}
