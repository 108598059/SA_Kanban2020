package domain.adapters.controller.workflow.output;

import domain.usecase.board.commit.CommitWorkflowOutput;

public class CommitWorkflowOutputImpl implements CommitWorkflowOutput {

    private String workflowId;

    public void setWorkflowId(String id) {
        this.workflowId = id;
    }

    public String getWorkflowId() {
        return workflowId;
    }
}
