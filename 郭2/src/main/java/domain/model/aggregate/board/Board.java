package domain.model.aggregate.board;

import domain.model.DomainEventPoster;
import domain.model.aggregate.board.event.BoardCreated;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Board extends DomainEventPoster {
    private List<String> workflowIds;
    private String boardId;
    private String boardName;

    public Board(){}

    public Board(String boardName){
        workflowIds = new ArrayList<String>();
        this.boardId = UUID.randomUUID().toString();
        this.boardName = boardName;
        addDomainEvent(new BoardCreated(boardId,boardName));
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardName() {
        return boardName;
    }

    public void addWorkflowId(String workflowId) {
        workflowIds.add(workflowId);
    }

    public void setWorkflowIds(List<String> workflowIds) {
        this.workflowIds = workflowIds;
    }

    public List<String> getWorkflowIds() {
        return workflowIds;
    }
}
