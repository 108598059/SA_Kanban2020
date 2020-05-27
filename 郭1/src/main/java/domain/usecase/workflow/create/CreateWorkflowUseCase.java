package domain.usecase.workflow.create;


import domain.entity.DomainEventBus;
import domain.entity.workflow.Workflow;
import domain.usecase.workflow.WorkflowRepository;



public class CreateWorkflowUseCase {

    private WorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    public CreateWorkflowUseCase(WorkflowRepository workflowRepository, DomainEventBus eventBus){
        this.workflowRepository = workflowRepository;
        this.eventBus = eventBus;
    }


    public void execute(CreateWorkflowInput createWorkflowInput, CreateWorkflowOutput createWorkflowOutput) {
        Workflow newWorkflow = new Workflow(createWorkflowInput.getBoardId());


        newWorkflow.setName(createWorkflowInput.getWorkflowName());

        workflowRepository.add(newWorkflow);
        eventBus.postAll(newWorkflow);

        createWorkflowOutput.setWorkFlowId(newWorkflow.getId());

    }

}
