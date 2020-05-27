package phd.sa.csie.ntut.edu.tw.model.board.event;

import phd.sa.csie.ntut.edu.tw.model.AbstractDomainEvent;

public class CardLeftColumnEvent extends AbstractDomainEvent {
    public CardLeftColumnEvent(String sourceID, String cardID, String columnID) {
        super(sourceID, "[Card Left Column Event] Card: " + cardID + " left the column: " + columnID);
    }
}
