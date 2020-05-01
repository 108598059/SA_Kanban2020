package ddd.kanban.domain.model.board.event;

import ddd.kanban.domain.model.DomainEvent;
import ddd.kanban.domain.model.common.DateProvider;

import java.util.Date;

public class BoardCreated implements DomainEvent {
    private String boardId;
    private Date occurredOn;

    public BoardCreated(String boardId){
        this.boardId = boardId;
        this.occurredOn = DateProvider.now();
    }

    @Override
    public Date occurredOn() {
        return null;
    }

    public String getBoardId() {
        return boardId;
    }
}
