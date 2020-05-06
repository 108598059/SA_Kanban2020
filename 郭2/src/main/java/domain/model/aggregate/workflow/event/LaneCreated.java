package domain.model.aggregate.workflow.event;

import domain.model.DomainEvent;
import domain.model.aggregate.workflow.Lane;

abstract public class LaneCreated implements DomainEvent {
    private String boardId;
    private String workflowId;
    private String laneId;

    public LaneCreated(String boardId, String workflowId, String laneId) {
        this.boardId = boardId;
        this.laneId = laneId;
        this.workflowId = workflowId;
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

    public String detail() {
        return "LaneCreated " + "workflowId = " + workflowId;
    }
}
