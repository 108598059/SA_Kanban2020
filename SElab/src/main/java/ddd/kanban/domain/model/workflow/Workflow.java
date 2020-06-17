package ddd.kanban.domain.model.workflow;

import ddd.kanban.domain.model.AggregateRoot;
import ddd.kanban.domain.model.card.event.CardCommitted;
import ddd.kanban.domain.model.card.event.CardUnCommitted;
import ddd.kanban.domain.model.workflow.event.WorkflowCreated;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class Workflow extends AggregateRoot {

    private List<Column> columns;
    private String boardId;

    public Workflow(String id, String title, String boardId){
        super(id, title);
        this.boardId = boardId;
        columns = new ArrayList<>();
        addDomainEvent(new WorkflowCreated(id, boardId, title, UUID.randomUUID().toString()));
    }

    public Workflow(String id, String title, String boardId, List<Column> columns){
        super(id, title);
        this.boardId = boardId;
        this.columns = columns;
    }

    public String createColumn(String columnName, String workflowId){
        Column column = new Column(UUID.randomUUID().toString(), columnName, workflowId);
        columns.add(column);
        return column.getId();
    }

    public Lane findColumnById(String columnId){
        return columns.stream()
                .filter(judgeColumnId(columnId))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public List<Column> getColumns(){
        return columns;
    }

    public static Predicate<Lane> judgeColumnId(String columnId){
        return column -> column.getId().equals(columnId);
    }

    public String getBoardId(){return boardId;}

    public String commitCard(String cardId, String laneId) {
        Lane column = this.findColumnById(laneId);
        return column.commitCard(cardId);
    }

    public String moveCard(String cardId, String fromLaneId, String toLaneId) {
        Lane fromLane = findColumnById(fromLaneId);
        Lane toLane = findColumnById(toLaneId);

        fromLane.unCommitCard(cardId);
        toLane.commitCard(cardId);

        addDomainEvent(new CardUnCommitted(cardId, this.id, fromLane.getId(), fromLane.getTitle(), UUID.randomUUID().toString()));
        addDomainEvent(new CardCommitted(cardId, this.id, toLane.getId(), toLane.getTitle(), UUID.randomUUID().toString()));

        return cardId;
    }
}
