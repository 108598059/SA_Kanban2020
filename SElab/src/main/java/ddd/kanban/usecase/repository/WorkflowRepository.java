package ddd.kanban.usecase.repository;

import ddd.kanban.domain.model.workflow.Workflow;

public interface WorkflowRepository {
    void add(Workflow workflow);

    Workflow findById(String workflowId);

    void save();
}
