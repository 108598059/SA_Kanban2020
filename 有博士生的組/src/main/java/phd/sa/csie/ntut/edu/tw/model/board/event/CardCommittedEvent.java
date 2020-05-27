package phd.sa.csie.ntut.edu.tw.model.board.event;

import phd.sa.csie.ntut.edu.tw.model.AbstractDomainEvent;

public class CardCommittedEvent extends AbstractDomainEvent {
    public CardCommittedEvent(String boardID, String cardID) {
        super(boardID, "[Card Committed] Card: " + cardID + " is committed to board: " + boardID);
    }
}
