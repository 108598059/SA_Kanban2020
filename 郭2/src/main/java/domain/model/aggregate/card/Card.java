package domain.model.aggregate.card;

import domain.model.DomainEventHolder;
import domain.model.aggregate.card.event.CardCreated;
import domain.model.aggregate.card.event.TaskCreated;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Card extends DomainEventHolder {
    private String cardId;
    private String cardName;
    private String cardContent;
    private String cardType;
    private String workflowId;
    private String laneId;
    private List<Task> taskList;

    public Card(String cardName, String workflowId, String laneId){
        taskList = new ArrayList<Task>();
        this.workflowId = workflowId;
        this.laneId = laneId;
        this.cardName = cardName;
        this.cardId = UUID.randomUUID().toString();
        addDomainEvent(new CardCreated(workflowId,laneId,this.cardId));
    }

    public Card(String cardName){
        taskList = new ArrayList<Task>();
        this.cardName = cardName;
        this.cardId = UUID.randomUUID().toString();
    }

    public Task getTaskById(String taskId) throws CloneNotSupportedException {
        for (Task each : taskList) {
            if (each.getTaskId().equalsIgnoreCase(taskId)) {
                return (Task) each.clone();
            }
        }
        throw new RuntimeException("Task is not found,id=" + taskId);
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardContent(String cardContent) {
        this.cardContent = cardContent;
    }

    public String getCardContent() {
        return cardContent;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardType() {
        return cardType;
    }

    public void setLaneId(String laneId) {
        this.laneId = laneId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public Task createTask(String cardId, String taskName) throws CloneNotSupportedException {
        Task task = new Task(cardId, taskName);
        taskList.add(task);
        addDomainEvent(new TaskCreated(task.getTaskId(), taskName, cardId));
        return (Task) task.clone();
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public List<Task> getTaskList() {
        List<Task> clonedTaskList = new ArrayList<Task>(taskList);
        return clonedTaskList;
    }

    public void addTaskId(Task task) {
        taskList.add(task);
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getLaneId() {
        return laneId;
    }
}
