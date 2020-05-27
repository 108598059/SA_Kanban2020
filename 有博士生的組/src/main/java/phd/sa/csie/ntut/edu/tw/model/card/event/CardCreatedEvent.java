package phd.sa.csie.ntut.edu.tw.model.card.event;

import phd.sa.csie.ntut.edu.tw.model.AbstractDomainEvent;
import phd.sa.csie.ntut.edu.tw.model.Entity;
import phd.sa.csie.ntut.edu.tw.model.card.Card;

import java.util.UUID;

public class CardCreatedEvent extends AbstractDomainEvent {
    UUID boardID;

    public CardCreatedEvent(String cardID, Entity entity, String boardID) {
        super(cardID, "[Card Created Event] Card: " + cardID + " is created at the board: " + boardID, entity);
        this.boardID = UUID.fromString(boardID);
    }

    @Override
    public Card getEntity() {
        return (Card) super.getEntity();
    }

    public UUID getBoardID() {
        return boardID;
    }
}
