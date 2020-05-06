package domain.model.aggregate.workflow.event;

import domain.model.DomainEvent;

public class WorkflowCreated implements DomainEvent {
    private String workflowId;
    private String boardId;

    public String getBoardId() {
        return boardId;
    }

    public WorkflowCreated(String boardId, String workflowId){
        this.workflowId = workflowId;
        this.boardId = boardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String detail() {
        return "WorkflowCreated " + "workflowId = " + workflowId;
    }
}
