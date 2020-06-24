package domain.adapters.controller.workflow.input;

import domain.usecase.workflow.create.CreateSwimlaneInput;

public class CreateSwimlaneInputImpl implements CreateSwimlaneInput {

    private String name;
    private String stageId;
    private String workflowId;

    public void setName(String name) {
        this.name = name;
    }

    public void setStageId(String Id) {
        this.stageId = Id;
    }

    public void setWorkflowId(String id) {
        this.workflowId = id;
    }

    public String getName() {
        return name;
    }

    public String getStageId() {
        return stageId;
    }

    public String getWorkflowId() {
        return workflowId;
    }
}
