package domain.adapters.controller.workflow;

import domain.usecase.workflow.create.CreateWorkflowOutput;

public class CreateWorkflowOutputImpl implements CreateWorkflowOutput {
    private String workflowId;

    public String getWorkflowId() {
        return this.workflowId;
    }

    public void setWorkFlowId(String id) {
        this.workflowId = id;
    }
}
