package phd.sa.csie.ntut.edu.tw.model.aggregate.board.event.move;

import phd.sa.csie.ntut.edu.tw.model.domain.AbstractDomainEvent;

import java.util.Date;

public class CardLeftColumnEvent extends AbstractDomainEvent {
    private String cardID;
    private String columnID;

    public CardLeftColumnEvent(String sourceID, String cardID, String columnID) {
        super(sourceID, "[Card Left Column Event] Card: " + cardID + " left the column: " + columnID);
        this.cardID = cardID;
        this.columnID = columnID;
    }

    public CardLeftColumnEvent(Date occurredOn, String sourceID, String sourceName, String id, String cardID, String columnID) {
        super(occurredOn, sourceID, sourceName, id);
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
