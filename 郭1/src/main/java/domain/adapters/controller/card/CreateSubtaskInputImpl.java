package domain.adapters.controller.card;

import domain.usecase.subtask.create.CreateSubtaskInput;

public class CreateSubtaskInputImpl implements CreateSubtaskInput {
    private String taskName;
    private String cardId;
    public void setSubtaskName(String name) {
        this.taskName = name;
    }

    public String getSubtaskName() {
        return this.taskName;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardId() {
        return this.cardId;
    }
}
