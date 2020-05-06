package phd.sa.csie.ntut.edu.tw.domain.model.board.event;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.domain.model.AbstractDomainEvent;

public class ColumnEnteredEvent extends AbstractDomainEvent {

    private UUID columnId;

    public ColumnEnteredEvent(String sourceId, UUID columnId) {
        super(sourceId, "Entered column event: " + columnId);
        this.columnId = columnId;
    }

    public UUID getColumnId() {
        return this.columnId;
    }
}
