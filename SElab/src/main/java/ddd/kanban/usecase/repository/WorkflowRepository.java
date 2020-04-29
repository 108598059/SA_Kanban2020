package ddd.kanban.usecase.repository;

import ddd.kanban.domain.model.workflow.Workflow;

import java.util.List;

public interface WorkflowRepository {
    void add(Workflow workflow);

    Workflow findById(String workflowId);

    void save();

    List<Workflow> findAll();
}
