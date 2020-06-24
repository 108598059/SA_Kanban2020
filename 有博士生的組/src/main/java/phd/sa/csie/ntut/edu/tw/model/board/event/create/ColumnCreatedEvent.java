package phd.sa.csie.ntut.edu.tw.model.board.event.create;

import phd.sa.csie.ntut.edu.tw.model.domain.AbstractDomainEvent;

public class ColumnCreatedEvent extends AbstractDomainEvent {
    public ColumnCreatedEvent(String boardID, String columnID, String columnTitle) {
        super(boardID, "[Column Created Event] Column: " + columnID + " is created on board: " + boardID +
                " with column title: \"" + columnTitle + "\"");
    }
}
