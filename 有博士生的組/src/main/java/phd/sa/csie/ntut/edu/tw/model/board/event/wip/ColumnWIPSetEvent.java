package phd.sa.csie.ntut.edu.tw.model.board.event.wip;

import phd.sa.csie.ntut.edu.tw.model.domain.AbstractDomainEvent;

public class ColumnWIPSetEvent extends AbstractDomainEvent {
    public ColumnWIPSetEvent(String boardID, String columnID, int wip) {
        super(boardID, "[Column WIP Set Event] The WIP of column: " + columnID + " is set as " + wip);
    }
}
