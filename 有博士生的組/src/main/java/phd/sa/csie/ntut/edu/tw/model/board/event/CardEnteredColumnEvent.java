package phd.sa.csie.ntut.edu.tw.model.board.event;

import phd.sa.csie.ntut.edu.tw.model.AbstractDomainEvent;

public class CardEnteredColumnEvent extends AbstractDomainEvent {

    private String columnID;
    private String cardID;

    public CardEnteredColumnEvent(String sourceID, String columnID, String cardID) {
        super(sourceID, "Entered column event: " + columnID);
        this.columnID = columnID;
        this.cardID = cardID;
    }

    public String getColumnID() {
        return this.columnID;
    }

    public String getCardID() {
        return cardID;
    }
}
