package ddd.kanban.domain.model.card.event;

import ddd.kanban.domain.model.DomainEvent;
import ddd.kanban.domain.model.common.DateProvider;

import java.util.Date;

public class CardCreated implements DomainEvent {
    private String cardId;
    private String workflowId;
    private String laneId;
    private Date occurredOn;

    public CardCreated(String cardId, String workflowId, String laneId){
        this.cardId = cardId;
        this.workflowId = workflowId;
        this.laneId = laneId;
        occurredOn = DateProvider.now();
    }

    @Override
    public Date occurredOn() {
        return occurredOn;
    }

    public String getCardId() {
        return cardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getLaneId() {
        return laneId;
    }
}
