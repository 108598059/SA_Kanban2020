package ddd.kanban.domain.model.card.card.event;

import ddd.kanban.domain.model.AbstractDomainEvent;

public class CardCreated extends AbstractDomainEvent {
    private String workflowId;
    private String columnId;

    public CardCreated(String cardId, String workflowId, String ColumnId, String id){
        super(cardId, id);
        this.workflowId = workflowId;
        this.columnId = ColumnId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getColumnId() {
        return columnId;
    }
}
