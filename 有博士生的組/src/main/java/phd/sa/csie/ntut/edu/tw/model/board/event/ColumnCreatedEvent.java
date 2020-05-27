package phd.sa.csie.ntut.edu.tw.model.board.event;

import phd.sa.csie.ntut.edu.tw.model.AbstractDomainEvent;

public class ColumnCreatedEvent extends AbstractDomainEvent {
    public ColumnCreatedEvent(String boardID, String columnID, String columnTitle) {
        super(boardID, "[Column Created Event] column: " + columnID + " is created on board: " + boardID +
                " with column title: \"" + columnTitle + "\"");
    }
}
