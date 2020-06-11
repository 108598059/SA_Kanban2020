package domain.model.aggregate.board.event;

import domain.model.DomainEvent;
import domain.model.common.DateProvider;

import java.util.Date;

public class WorkflowCommited implements DomainEvent {
    private String workflowId;
    private String boardId;
    private Date occurredOn;

    public WorkflowCommited(String workflowId, String boardId) {
        this.workflowId = workflowId;
        this.boardId = boardId;
        occurredOn = DateProvider.now();
    }

    @Override
    public Date getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String detail() {
        return "WorkflowCommited " + "workflowId = " + workflowId;
    }
}

