package ddd.kanban.domain.model.kanbanboard.workflow.event;

import ddd.kanban.domain.model.AbstractDomainEvent;

public class WorkflowCreated extends AbstractDomainEvent {
    private String boardId;

    public WorkflowCreated(String workflowId, String boardId, String workflowTitle, String id){
        super(workflowId, id);
        this.boardId = boardId;
    }

    public String getBoardId() {
        return boardId;
    }
}
