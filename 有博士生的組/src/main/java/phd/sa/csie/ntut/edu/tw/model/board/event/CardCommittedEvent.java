package phd.sa.csie.ntut.edu.tw.model.board.event;

import phd.sa.csie.ntut.edu.tw.model.AbstractDomainEvent;
import phd.sa.csie.ntut.edu.tw.model.Entity;

public class CardCommittedEvent extends AbstractDomainEvent {
    private String boardID;
    private String cardID;

    public CardCommittedEvent(String sourceID, String sourceName, String boardID, String cardID) {
        super(sourceID, sourceName);
        this.boardID = boardID;
        this.cardID = cardID;
    }
}
