package kanban.domain.usecase.workflow.repository;

import kanban.domain.usecase.workflow.WorkflowEntity;

public interface IWorkflowRepository {

    void add(WorkflowEntity workflowEntity);

    WorkflowEntity getWorkflowById(String workflowId);

    void save(WorkflowEntity workflowEntity);
}
