package domain.adapters.controller.card;

import domain.usecase.subtask.create.CreateSubtaskOutput;

public class CreateSubtaskOutputImpl implements CreateSubtaskOutput {
    private String taskId;
    public void setSubtaskId(String id) {
        this.taskId = id;
    }

    public String getSubtaskId() {
        return this.taskId;
    }
}
