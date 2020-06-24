package ddd.kanban.domain.model;

abstract public class FlowEvent extends AbstractDomainEvent {
    private final String workflowId;
    private final String cardId;

    public FlowEvent(String cardId, String workflowId, String columnId, String id) {
        super(columnId, id);
        this.workflowId = workflowId;
        this.cardId = cardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getCardId() {
        return cardId;
    }
}
