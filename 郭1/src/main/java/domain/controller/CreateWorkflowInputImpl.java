package domain.controller;

import domain.usecase.workflow.create.CreateWorkflowInput;

public class CreateWorkflowInputImpl implements CreateWorkflowInput {

    private String workflowName;
    private String workflowId;

    private String boardId;

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }
    public String getWorkflowName() {
        return this.workflowName;
    }

    public String getBoardId() {
        return this.boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }
}
