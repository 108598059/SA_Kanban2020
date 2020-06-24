package domain.entity.aggregate.board.event;

import domain.entity.DomainEvent;

public class WorkflowCommitted implements DomainEvent {
    private String workflowId ;
    private String boardId ;

    public WorkflowCommitted( String workflowId, String boardId ) {
        this.workflowId = workflowId ;
        this.boardId = boardId ;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getBoardId() {
        return boardId;
    }
}
