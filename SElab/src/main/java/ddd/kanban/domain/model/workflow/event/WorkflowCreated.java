package ddd.kanban.domain.model.workflow.event;

import ddd.kanban.domain.model.DomainEvent;
import ddd.kanban.domain.model.common.DateProvider;

import java.util.Date;

public class WorkflowCreated implements DomainEvent {
    private String boardId;
    private String workflowId;
    private Date OccurredOn;

    public WorkflowCreated(String workflowId, String boardId){
        this.workflowId = workflowId;
        this.boardId = boardId;
        this.OccurredOn = DateProvider.now();
    }

    public String getBoardId() {
        return boardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    @Override
    public Date occurredOn() {
        return occurredOn();
    }
}
