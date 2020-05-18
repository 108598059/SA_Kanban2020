package phd.sa.csie.ntut.edu.tw.model.board.event;

import phd.sa.csie.ntut.edu.tw.model.AbstractDomainEvent;

public class CardLeaveColumnEvent extends AbstractDomainEvent {
    public CardLeaveColumnEvent(String sourceID, String columnID) {
        super(sourceID, "Leaved column event: " + columnID);
    }
}
