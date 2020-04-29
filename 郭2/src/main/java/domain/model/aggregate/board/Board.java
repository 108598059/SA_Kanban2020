package domain.model.aggregate.board;

import domain.model.aggregate.AggregateRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Board extends AggregateRoot {
    private List<String> workflowIds;
    private String boardId;
    private String boardName;

    public Board(String boardName){
        workflowIds = new ArrayList<String>();
        this.boardId = UUID.randomUUID().toString();
        this.boardName = boardName;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardName() {
        return boardName;
    }

    public String getBoardId() {
        return boardId;
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
