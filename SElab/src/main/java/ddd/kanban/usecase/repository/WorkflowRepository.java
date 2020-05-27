package ddd.kanban.usecase.repository;

import ddd.kanban.domain.model.workflow.Workflow;
import ddd.kanban.usecase.workflow.entity.WorkflowEntity;

import java.util.List;

public interface WorkflowRepository {
    void add(WorkflowEntity workflowEntity);

    WorkflowEntity findById(String workflowId);

    void save(WorkflowEntity workflowEntity);

    List<WorkflowEntity> findAll();
}
