package phd.sa.csie.ntut.edu.tw.model.board.event.move;

import phd.sa.csie.ntut.edu.tw.model.domain.AbstractDomainEvent;

public class CardEnteredColumnEvent extends AbstractDomainEvent {
    private String cardID;
    private String toColumnID;
    private String fromColumnID;
    private String boardID;

    public CardEnteredColumnEvent(String boardID, String cardID, String toColumnID, String fromColumnID) {
        super(boardID, "[Card Entered Event] Card: " + cardID + " entered column: " + toColumnID);
        this.cardID = cardID;
        this.toColumnID = toColumnID;
        this.fromColumnID = fromColumnID;
        this.boardID = boardID;
    }

    public String getCardID() {
        return cardID;
    }

    public String getToColumnID() {
        return toColumnID;
    }

    public String getFromColumnID() {
        return fromColumnID;
    }

    public String getBoardID() {
        return boardID;
    }
}
