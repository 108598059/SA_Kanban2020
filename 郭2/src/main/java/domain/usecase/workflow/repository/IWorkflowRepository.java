package domain.usecase.workflow.repository;

import domain.usecase.workflow.WorkflowDTO;

public interface IWorkflowRepository {
    void add(WorkflowDTO workflowDTO);

    WorkflowDTO getWorkflowById(String workflowId);

    void save(WorkflowDTO workflowDTO);
}
