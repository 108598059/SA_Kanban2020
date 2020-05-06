package ddd.kanban.usecase.card.create;

import ddd.kanban.domain.model.card.CardType;

import java.util.Date;
import java.util.List;

public class CreateTaskInput {

    private String taskTitle;
    private String cardId;

    public CreateTaskInput(String taskTitle, String cardId) {
        this.taskTitle = taskTitle;
        this.cardId = cardId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public String getCardId() {
        return cardId;
    }
}
