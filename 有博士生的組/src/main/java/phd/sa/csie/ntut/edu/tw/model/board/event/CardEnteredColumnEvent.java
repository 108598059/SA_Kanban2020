package phd.sa.csie.ntut.edu.tw.model.board.event;

import phd.sa.csie.ntut.edu.tw.model.AbstractDomainEvent;

public class CardEnteredColumnEvent extends AbstractDomainEvent {

    public CardEnteredColumnEvent(String sourceID, String cardID, String columnID) {
        super(sourceID, "[Card Entered Event] card: " + cardID + " entered column: " + columnID);
    }
}
