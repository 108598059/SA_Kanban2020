package ddd.kanban.domain.model.card.card;

import ddd.kanban.domain.model.AggregateRoot;
import ddd.kanban.domain.model.card.card.event.CardCreated;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class Card extends AggregateRoot {
    private List<Task> tasks;
    private String boardId;
    private String workflowId;
    private String columnId;
    private String description;
    private CardType cardType;
    private List<String> tags;
    private List<String> assignUsers;
    private Date plannedStartDate;
    private Date plannedFinishDate;
    private int priority;

    public Card(final String id, String title, String boardId, String workflowId, String ColumnId) {
        super(id, title);
        this.boardId = boardId;
        this.workflowId = workflowId;
        this.columnId = ColumnId;
        tasks = new ArrayList<>();
        addDomainEvent(new CardCreated(id, workflowId, ColumnId, UUID.randomUUID().toString()));
    }

    public Card(final String id, String title, String boardId, String workflowId, String ColumnId, List<Task> tasks) {
        super(id, title);
        this.boardId = boardId;
        this.workflowId = workflowId;
        this.columnId = ColumnId;
        this.tasks = tasks;
    }

    public String createTask(String taskTitle, String cardId) {
        Task task = new Task(UUID.randomUUID().toString(), taskTitle);
        tasks.add(task);
        return task.getId();
    }

    public Task findTaskById(String taskId) {
        return tasks.stream()
                .filter(given(taskId))
                .findFirst()
                .orElseThrow(RuntimeException::new);

    }

    public static Predicate<Task> given(String id) {
        return task -> task.getId().equals(id);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getAssignUsers() {
        return assignUsers;
    }

    public void setAssignUsers(List<String> assignUsers) {
        this.assignUsers = assignUsers;
    }

    public Date getPlannedStartDate() {
        return plannedStartDate;
    }

    public void setPlannedStartDate(Date plannedStartDate) {
        this.plannedStartDate = plannedStartDate;
    }

    public Date getPlannedFinishDate() {
        return plannedFinishDate;
    }

    public void setPlannedFinishDate(Date plannedFinishDate) {
        this.plannedFinishDate = plannedFinishDate;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String ColumnId) {
        this.columnId = ColumnId;
    }
}
