package ddd.kanban.domain.model.board;


import ddd.kanban.domain.model.AggregateRoot;
import ddd.kanban.domain.model.board.event.BoardCreated;

import java.util.ArrayList;
import java.util.List;

public class Board extends AggregateRoot {

    private String description;
    private List<String> workflowIds;

    public Board(final String id, String title, String description) {
        super(id, title);
        this.description = description;
        this.workflowIds = new ArrayList<>();
        addDomainEvent(new BoardCreated(this.id));
    }

    public String getDescription() {
        return description;
    }

    public List<String> getWorkflowIds() {
        return this.workflowIds;
    }

    public void setWorkflowIds(List<String> workflowIds) {
        this.workflowIds = workflowIds;
    }

    public String commitWorkflow(String workflowId) {
        if (isWorkflowExist(workflowId))
            throw new RuntimeException("workflow already exist");
        this.workflowIds.add(workflowId);
        return workflowId;
    }

    private boolean isWorkflowExist(String workflowId) {
        return this.workflowIds.stream()
                .anyMatch(id -> id.equals(workflowId));
    }
}
