package phd.sa.csie.ntut.edu.tw.model.aggregate.board.event.create;

import phd.sa.csie.ntut.edu.tw.model.domain.AbstractDomainEvent;

public class BoardCreatedEvent extends AbstractDomainEvent {
    public BoardCreatedEvent(String boardID, String boardName) {
        super(boardID, "[Board Created Event] Board: " + boardID + " is created with name: " + boardName);
    }
}
