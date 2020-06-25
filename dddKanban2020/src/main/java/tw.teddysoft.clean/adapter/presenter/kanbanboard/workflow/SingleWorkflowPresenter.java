package tw.teddysoft.clean.adapter.presenter.kanbanboard.workflow;

import tw.teddysoft.clean.usecase.kanbanboard.workflow.create.CreateWorkflowOutput;

public class SingleWorkflowPresenter implements CreateWorkflowOutput {

    private String workflowName;
    private String workflowId;

    @Override
    public String getWorkflowName() {
        return workflowName;
    }

    @Override
    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    @Override
    public String getWorkflowId() {
        return workflowId;
    }

    @Override
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }
}
