package domain.model.aggregate.board;

import domain.model.aggregate.AggregateRoot;
import domain.model.aggregate.board.event.BoardCreated;
import domain.model.aggregate.board.event.WorkflowCommited;
import domain.model.aggregate.card.Task;
import domain.model.aggregate.workflow.Lane;
import domain.model.aggregate.workflow.Workflow;
import domain.model.aggregate.workflow.event.CardUnCommitted;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Board extends AggregateRoot {
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
        if (null == workflowId)
            throw new RuntimeException("Cannot commit a workflow in board '" + workflowId + "'");

        workflowIds.add(workflowId);

        addDomainEvent(new WorkflowCommited(workflowId, boardId));
    }

    public void setWorkflowIds(List<String> workflowIds) {
        this.workflowIds = workflowIds;
    }

    public List<String> getWorkflowIds() {
        return workflowIds;
    }


}
