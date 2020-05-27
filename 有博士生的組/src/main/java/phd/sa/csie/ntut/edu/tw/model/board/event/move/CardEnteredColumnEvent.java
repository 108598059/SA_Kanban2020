package phd.sa.csie.ntut.edu.tw.model.board.event.move;

import phd.sa.csie.ntut.edu.tw.model.domain.AbstractDomainEvent;

public class CardEnteredColumnEvent extends AbstractDomainEvent {
    private String cardID;
    private String columnID;

    public CardEnteredColumnEvent(String sourceID, String cardID, String columnID) {
        super(sourceID, "[Card Entered Event] Card: " + cardID + " entered column: " + columnID);
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
