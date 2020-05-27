package phd.sa.csie.ntut.edu.tw.model.board.event;

import phd.sa.csie.ntut.edu.tw.model.AbstractDomainEvent;

public class CardLeftColumnEvent extends AbstractDomainEvent {
    public CardLeftColumnEvent(String sourceID, String columnID) {
        super(sourceID, "Left column event: " + columnID);
    }
}
