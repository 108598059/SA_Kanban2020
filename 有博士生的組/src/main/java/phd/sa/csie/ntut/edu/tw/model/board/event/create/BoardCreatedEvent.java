package phd.sa.csie.ntut.edu.tw.model.board.event.create;

import phd.sa.csie.ntut.edu.tw.model.domain.AbstractDomainEvent;

public class BoardCreatedEvent extends AbstractDomainEvent {
    public BoardCreatedEvent(String boardID, String boardName) {
        super(boardID, "[Board Created] Board: " + boardID + " is created with name: " + boardName);
    }
}
