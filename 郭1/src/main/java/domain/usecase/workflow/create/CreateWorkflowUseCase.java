package domain.usecase.workflow.create;

import domain.entity.Workflow;
import domain.adapter.WorkflowRepositoryImpl;
import domain.usecase.workflow.WorkflowRepository;


public class CreateWorkflowUseCase {

    private WorkflowRepository workflowRepository;

    public CreateWorkflowUseCase(WorkflowRepository workflowRepository){
        this.workflowRepository = workflowRepository;
    }

    public void execute(CreateWorkflowInput createWorkflowInput, CreateWorkflowOutput createWorkflowOutput) {
        Workflow newWorkflow = new Workflow();

        newWorkflow.setName(createWorkflowInput.getWorkflowName());
        workflowRepository.save(newWorkflow);

        createWorkflowOutput.setWorkFlowId(newWorkflow.getId());

    }

}
