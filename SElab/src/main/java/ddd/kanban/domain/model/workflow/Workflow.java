package ddd.kanban.domain.model.workflow;

import ddd.kanban.domain.model.AggregateRoot;
import ddd.kanban.domain.model.workflow.event.WorkflowCreated;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class Workflow extends AggregateRoot {

    private List<Lane> columns;
    private String boardId;

    public Workflow(String id, String title, String boardId){
        super(id, title);
        this.boardId = boardId;
        columns = new ArrayList<Lane>();
        addDomainEvent(new WorkflowCreated(id, boardId));
    }

    public String createColumn(String columnName, String workflowId){
        Lane column = new Column(UUID.randomUUID().toString(), columnName, workflowId);
        columns.add(column);
        return column.getId();
    }

    public Lane findColumnById(String columnId){
        return columns.stream()
                .filter(judgeColumnId(columnId))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public List<Lane> getColumns(){
        return columns;
    }

    public static Predicate<Lane> judgeColumnId(String columnId){
        return column -> column.getId().equals(columnId);
    }

    public String getBoardId(){return boardId;}

    public String commitCard(String cardId, String laneId) {
        Lane column = this.findColumnById(laneId);
        return column.commitCard(cardId, this.id);
    }
}
