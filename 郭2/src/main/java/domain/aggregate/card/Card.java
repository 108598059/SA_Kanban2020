package domain.aggregate.card;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Card {
    private String cardId;
    private String cardName;
    private String cardContent;
    private String cardType;
    private List<Task> taskList;

    public Card(String cardName){
        taskList = new ArrayList<Task>();
        this.cardName = cardName;
        this.cardId = UUID.randomUUID().toString();
    }

    public Task getTaskById(String taskId) {
        for (Task each : taskList) {
            if (each.getTaskId().equalsIgnoreCase(taskId)) {
                return each;
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

    public Task createTask(String cardId, String taskName) {
        Task task = new Task(cardId, taskName);
        taskList.add(task);
        return task;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void addTaskId(Task task) {
        taskList.add(task);
    }
}
