package ddd.kanban.domain.model.card;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class Card {
    private final List<Task> tasks;
    private String id;

    private String title;
    private String description;
    private CardType cardType;
    private List<String> tags;
    private List<String> assignUsers;
    private Date plannedStartDate;
    private Date plannedFinishDate;
    private int priority;

    public Card(String id, String title)
    {
        this.id = id;
        this.title = title;

        tasks = new ArrayList<Task>();
    }

    public Card(String id, String title, String description, CardType cardType, List<String> tags, List<String>assignUsers, Date plannedStartDate, Date plannedFinishDate, int priority){
        this.id = id;
        this.title = title;
        this.description = description;
        this.cardType = cardType;
        this.tags = tags;
        this.assignUsers = assignUsers;
        this.plannedStartDate = plannedStartDate;
        this.plannedFinishDate = plannedFinishDate;
        this.priority = priority;

        tasks = new ArrayList<Task>();
    }

    public String createTask(Task task) {
        String taskId = UUID.randomUUID().toString();
        Task newTask = new Task(taskId, task.getTitle(), task.getDescription(), task.getTaskType(), task.getTags(), task.getAssignUsers(), task.getPlannedStartDate(), task.getPlannedFinishDate(), task.getHeader(), task.getPriority(), task.getExternalLink());

        tasks.add(newTask);

        return taskId;
    }

    public Task findTaskById(String taskId){
        return tasks.stream()
                .filter(given(taskId))
                .findFirst()
                .orElseThrow(RuntimeException::new);

    }

    public static Predicate<Task> given(String id){
        return task -> task.getId().equals(id);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() { return description; }

    public void setTitle(String title) {
        this.title = title;
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

}
