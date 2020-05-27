package ddd.kanban.domain.model.workflow.event;

import ddd.kanban.domain.model.AbstractDomainEvent;
import ddd.kanban.domain.model.DomainEvent;
import ddd.kanban.domain.model.common.DateProvider;

import java.util.Date;

public class WorkflowCreated extends AbstractDomainEvent {
    private String boardId;

    public WorkflowCreated(String workflowId, String boardId, String workflowTitle, String id){
        super(workflowId, workflowTitle, id);
        this.boardId = boardId;
    }

    public String getBoardId() {
        return boardId;
    }
}
