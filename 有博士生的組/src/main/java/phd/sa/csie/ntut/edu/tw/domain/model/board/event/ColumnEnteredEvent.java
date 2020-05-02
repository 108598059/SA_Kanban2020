package phd.sa.csie.ntut.edu.tw.domain.model.board.event;

import phd.sa.csie.ntut.edu.tw.domain.model.AbstractDomainEvent;

public class ColumnEnteredEvent extends AbstractDomainEvent {
    public ColumnEnteredEvent(String sourceId, String columnId) {
        super(sourceId, "Entered column event: " + columnId);
    }
}
