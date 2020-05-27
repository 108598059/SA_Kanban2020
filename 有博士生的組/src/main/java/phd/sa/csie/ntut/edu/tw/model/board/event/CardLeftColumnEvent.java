package phd.sa.csie.ntut.edu.tw.model.board.event;

import phd.sa.csie.ntut.edu.tw.model.AbstractDomainEvent;

public class CardLeftColumnEvent extends AbstractDomainEvent {
    private String cardID;
    private String columnID;

    public CardLeftColumnEvent(String sourceID, String cardID, String columnID) {
        super(sourceID, "[Card Left Column Event] Card: " + cardID + " left the column: " + columnID);
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
