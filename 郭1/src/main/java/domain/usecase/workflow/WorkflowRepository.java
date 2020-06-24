package domain.usecase.workflow;


import domain.entity.aggregate.workflow.Workflow;


public interface WorkflowRepository {

    WorkflowDTO getWorkFlowById(String id);
    void add(WorkflowDTO workflow);
    void save(WorkflowDTO workflow);


}
