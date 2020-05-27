package phd.sa.csie.ntut.edu.tw.model.board.event;

import phd.sa.csie.ntut.edu.tw.model.AbstractDomainEvent;

public class BoardCreatedEvent extends AbstractDomainEvent {
    public BoardCreatedEvent(String boardID, String boardName) {
        super(boardID, "[Board Created] board: " + boardID + " is created, the board name is " + boardName);
    }
}
