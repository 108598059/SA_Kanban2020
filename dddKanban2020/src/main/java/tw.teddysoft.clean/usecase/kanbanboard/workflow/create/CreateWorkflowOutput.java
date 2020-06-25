package tw.teddysoft.clean.usecase.kanbanboard.workflow.create;

import tw.teddysoft.clean.domain.usecase.Output;

public interface CreateWorkflowOutput extends Output {
    String getWorkflowName();
    void setWorkflowName(String workflowName);
    String getWorkflowId();
    void setWorkflowId(String WorkflowId);
}
