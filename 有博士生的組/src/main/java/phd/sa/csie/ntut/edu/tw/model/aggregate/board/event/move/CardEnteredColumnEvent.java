package phd.sa.csie.ntut.edu.tw.model.aggregate.board.event.move;

import phd.sa.csie.ntut.edu.tw.model.domain.AbstractDomainEvent;

import java.util.Date;

public class CardEnteredColumnEvent extends AbstractDomainEvent {
    private String cardID;
    private String toColumnID;
    private String boardID;

    public CardEnteredColumnEvent(String boardID, String cardID, String toColumnID) {
        super(boardID, "[Card Entered Event] Card: " + cardID + " entered column: " + toColumnID);
        this.cardID = cardID;
        this.toColumnID = toColumnID;
        this.boardID = boardID;
    }

    public CardEnteredColumnEvent(Date occurredOn, String sourceID, String sourceName, String id, String cardID, String toColumnID) {
        super(occurredOn, sourceID, sourceName, id);
        this.cardID = cardID;
        this.toColumnID = toColumnID;
        this.boardID = sourceID;
    }


    public String getCardID() {
        return cardID;
    }

    public String getToColumnID() {
        return toColumnID;
    }

    public String getBoardID() {
        return boardID;
    }
}
