package domain.adapters.controller.workflow;

import domain.usecase.stage.create.CreateStageInput;

public class CreateStageInputImpl implements CreateStageInput {
    private String stageName ;
    private String workFlowId;

    public void setStageName(String name) {
        this.stageName = name;
    }

    public String getStageName() {
        return this.stageName ;
    }

    public void setWorkflowId(String id){ this.workFlowId = id;}

    public String getWorkflowId(){ return this.workFlowId;}
}
