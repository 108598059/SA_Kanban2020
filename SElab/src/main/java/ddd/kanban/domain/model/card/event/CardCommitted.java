package ddd.kanban.domain.model.card.event;

import ddd.kanban.domain.model.DomainEvent;
import ddd.kanban.domain.model.common.DateProvider;

import java.util.Date;

public class CardCommitted implements DomainEvent {

    private Date occurredDate;
    private String laneId;
    private String cardId;

    public CardCommitted(String laneId, String cardId){
        this.laneId = laneId;
        this.cardId = cardId;
        this.occurredDate = DateProvider.now();
    }

    @Override
    public Date occurredOn() {
        return occurredDate;
    }

    public String getLaneId() {
        return laneId;
    }

    public String getCardId() {
        return cardId;
    }
}
