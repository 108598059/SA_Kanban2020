package domain.usecase.workflow;

import domain.entity.workflow.Workflow;

public interface WorkflowRepository {

    Workflow getWorkFlowById(String id);
    void add(Workflow workflow);
    void save(Workflow workflow);
}
