package domain.model.aggregate.board.event;

import domain.model.DomainEvent;
import domain.model.common.DateProvider;

import java.util.Date;

public class BoardCreated implements DomainEvent {
    private String boardId;
    private String boardName;
    private Date occurredOn;

    public BoardCreated(String userId, String boardId, String boardName){
        this.boardId = boardId;
        this.boardName = boardName;
        occurredOn = DateProvider.now();
    }

    public String getBoardName() {
        return boardName;
    }

    public String getBoardId() {
        return boardId;
    }

    @Override
    public Date getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String detail() {
        return "BoardCreated " + "boardId = " + boardId;
    }
}
