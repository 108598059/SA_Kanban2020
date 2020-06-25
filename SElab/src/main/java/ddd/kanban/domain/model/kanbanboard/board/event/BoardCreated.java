package ddd.kanban.domain.model.kanbanboard.board.event;

import ddd.kanban.domain.model.AbstractDomainEvent;

public class BoardCreated extends AbstractDomainEvent {

    public BoardCreated(String boardId, String boardTitle ,String id) {
        super(boardId, id);
    }
}
