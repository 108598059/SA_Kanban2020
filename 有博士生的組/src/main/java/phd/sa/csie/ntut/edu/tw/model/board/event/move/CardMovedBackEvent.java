package phd.sa.csie.ntut.edu.tw.model.board.event.move;

import phd.sa.csie.ntut.edu.tw.model.domain.AbstractDomainEvent;

public class CardMovedBackEvent extends AbstractDomainEvent {
    public CardMovedBackEvent(String sourceID, String columnID, String cardID) {
        super(sourceID, "[Card Moved Back Event] Card: " + cardID + " moved back to the column: " + columnID);
    }
}
