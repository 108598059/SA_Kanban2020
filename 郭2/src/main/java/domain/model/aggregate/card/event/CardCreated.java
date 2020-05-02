package domain.model.aggregate.card.event;

import domain.model.DomainEvent;

public class CardCreated extends DomainEvent {
    private String workflowId;
    private String laneId;
    private String cardId;

    public CardCreated(String workflowId, String laneId, String cardId) {
        this.workflowId = workflowId;
        this.laneId = laneId;
        this.cardId = cardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getLaneId() {
        return laneId;
    }

    public String getCardId() {
        return cardId;
    }
}
