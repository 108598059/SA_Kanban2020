package domain.usecase.workflow;


public interface WorkflowRepository {

    WorkflowDTO getWorkFlowById(String id);
    void add(WorkflowDTO workflow);
    void save(WorkflowDTO workflow);


}
