package ddd.kanban.usecase.card.create;

import ddd.kanban.domain.model.card.CardType;

import java.util.Date;
import java.util.List;

public class CreateTaskOutput {

    private String taskId;
    private String taskTitle;
    private String cardId;

    public CreateTaskOutput() {
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
