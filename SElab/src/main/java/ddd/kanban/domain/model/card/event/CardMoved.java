package ddd.kanban.domain.model.card.event;

import ddd.kanban.domain.model.AbstractDomainEvent;

public class CardMoved extends AbstractDomainEvent {
    private String toLaneId;

    public CardMoved(String cardId, String toLaneTitle, String toLaneId, String id){
        super(cardId, toLaneTitle, id);
        this.toLaneId = toLaneId;
    }

    public String getToLaneId() {
        return toLaneId;
    }
}
