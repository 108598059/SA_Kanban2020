package domain.adapters.controller.card.output;

import domain.usecase.card.create.CreateSubtaskOutput;

public class CreateSubtaskOutputImpl implements CreateSubtaskOutput {
    private String taskId;
    public void setSubtaskId(String id) {
        this.taskId = id;
    }

    public String getSubtaskId() {
        return this.taskId;
    }
}
