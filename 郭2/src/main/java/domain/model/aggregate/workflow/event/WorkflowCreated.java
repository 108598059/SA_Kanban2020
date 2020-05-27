package domain.model.aggregate.workflow.event;

import domain.model.DomainEvent;
import domain.model.common.DateProvider;

import java.util.Date;

public class WorkflowCreated implements DomainEvent {
    private String workflowId;
    private String boardId;
    private Date occurredOn;

    public String getBoardId() {
        return boardId;
    }

    public WorkflowCreated(String boardId, String workflowId){
        this.workflowId = workflowId;
        this.boardId = boardId;
        this.occurredOn = DateProvider.now();
    }

    public String getWorkflowId() {
        return workflowId;
    }

    @Override
    public Date getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String detail() {
        return "WorkflowCreated " + "workflowId = " + workflowId;
    }
}
