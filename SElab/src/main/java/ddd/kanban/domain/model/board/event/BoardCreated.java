package ddd.kanban.domain.model.board.event;

import ddd.kanban.domain.model.AbstractDomainEvent;
import ddd.kanban.domain.model.DomainEvent;
import ddd.kanban.domain.model.common.DateProvider;

import java.util.Date;

public class BoardCreated extends AbstractDomainEvent {

    public BoardCreated(String boardId, String boardTitle ,String id) {
        super(boardId, boardTitle, id);
    }
}
