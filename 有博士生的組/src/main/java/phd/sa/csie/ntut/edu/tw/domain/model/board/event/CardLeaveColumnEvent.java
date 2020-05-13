package phd.sa.csie.ntut.edu.tw.domain.model.board.event;

import phd.sa.csie.ntut.edu.tw.domain.model.AbstractDomainEvent;

public class CardLeaveColumnEvent extends AbstractDomainEvent {
    public CardLeaveColumnEvent(String sourceId, String columnId) {
        super(sourceId, "Leaved column event: " + columnId);
    }
}
