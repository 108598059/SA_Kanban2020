package phd.sa.csie.ntut.edu.tw.domain.model.board.event;

import phd.sa.csie.ntut.edu.tw.domain.model.AbstractDomainEvent;

public class CardLeaveColumn extends AbstractDomainEvent {
    public CardLeaveColumn(String sourceId, String columnId) {
        super(sourceId, "Leaved column event: " + columnId);
    }
}
