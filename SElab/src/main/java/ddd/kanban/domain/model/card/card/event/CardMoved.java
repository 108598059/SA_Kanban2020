package ddd.kanban.domain.model.card.card.event;

import ddd.kanban.domain.model.AbstractDomainEvent;

public class CardMoved extends AbstractDomainEvent {
    private String toColumnId;

    public CardMoved(String cardId, String toColumnId, String id){
        super(cardId, id);
        this.toColumnId = toColumnId;
    }

    public String getToColumnId() {
        return toColumnId;
    }
}
