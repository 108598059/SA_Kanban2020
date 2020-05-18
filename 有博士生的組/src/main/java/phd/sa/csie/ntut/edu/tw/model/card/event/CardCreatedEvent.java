package phd.sa.csie.ntut.edu.tw.model.card.event;

import phd.sa.csie.ntut.edu.tw.model.AbstractDomainEvent;
import phd.sa.csie.ntut.edu.tw.model.Entity;
import phd.sa.csie.ntut.edu.tw.model.card.Card;

import java.util.UUID;

public class CardCreatedEvent extends AbstractDomainEvent {
    private UUID boardID;
    public CardCreatedEvent(String sourceID,
                            String sourceName) {
        super(sourceID, sourceName);
    }

    public CardCreatedEvent(Entity entity, UUID boardID) {
        super(entity);
        this.boardID = boardID;
    }

    public UUID getBoardID() {
        return boardID;
    }

    @Override
    public Card getEntity() {
        return (Card) super.getEntity();
    }
}
