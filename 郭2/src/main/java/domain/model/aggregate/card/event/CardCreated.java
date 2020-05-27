package domain.model.aggregate.card.event;

import domain.model.DomainEvent;
import domain.model.common.DateProvider;

import java.util.Date;

public class CardCreated implements DomainEvent {
    private String workflowId;
    private String laneId;
    private String cardId;
    private Date occurredOn;

    public CardCreated(String workflowId, String laneId, String cardId) {
        this.workflowId = workflowId;
        this.laneId = laneId;
        this.cardId = cardId;
        occurredOn = DateProvider.now();
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

    @Override
    public Date getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String detail() {
        return "CardCreated " + "cardId = " + cardId;
    }
}
