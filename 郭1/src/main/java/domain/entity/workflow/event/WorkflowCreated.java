package domain.entity.workflow.event;

import domain.entity.DomainEvent;
import domain.entity.workflow.Workflow;

public class WorkflowCreated extends DomainEvent {
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


}
