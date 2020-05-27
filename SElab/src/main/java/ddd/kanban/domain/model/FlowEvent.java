package ddd.kanban.domain.model;

abstract public class FlowEvent extends AbstractDomainEvent {
    private final String workflowId;
    private final String cardId;

    public FlowEvent(String cardId, String workflowId, String laneId, String summary, String id) {
        super(laneId, summary, id);
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
