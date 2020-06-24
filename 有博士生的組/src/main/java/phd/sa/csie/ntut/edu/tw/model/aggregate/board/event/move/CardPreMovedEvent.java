package phd.sa.csie.ntut.edu.tw.model.aggregate.board.event.move;

import phd.sa.csie.ntut.edu.tw.model.domain.AbstractDomainEvent;

public class CardPreMovedEvent extends AbstractDomainEvent {

    private String boardID;
    private String cardID;
    private String fromColumnID;
    private String toColumnID;

    public CardPreMovedEvent(String boardID, String cardID, String fromColumnID, String toColumnID) {
        super(boardID, "[Card Pre-moved Event] Card: " + cardID + " pre-moved from the column: " +
                fromColumnID + " to the column: " + toColumnID);

        this.boardID = boardID;
        this.cardID = cardID;
        this.fromColumnID = fromColumnID;
        this.toColumnID = toColumnID;
    }

    public String getBoardID() {
        return boardID;
    }

    public String getCardID() {
        return cardID;
    }

    public String getFromColumnID() {
        return fromColumnID;
    }

    public String getToColumnID() {
        return toColumnID;
    }
}
