package phd.sa.csie.ntut.edu.tw.model.board.event;

import phd.sa.csie.ntut.edu.tw.model.AbstractDomainEvent;

public class CardEnteredColumnEvent extends AbstractDomainEvent {
    private String cardID;
    private String columnID;
    public static final String EVENT_NAME = "[Card Entered Event]";

    public CardEnteredColumnEvent(String sourceID, String cardID, String columnID) {
        super(sourceID, EVENT_NAME + " Card: " + cardID + " entered column: " + columnID);
        this.cardID = cardID;
        this.columnID = columnID;
    }

    public String getCardID() {
        return cardID;
    }

    public String getColumnID() {
        return columnID;
    }
}
