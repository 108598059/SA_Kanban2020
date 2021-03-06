package domain.usecase.stage.create;

import domain.model.DomainEventBus;
import domain.model.aggregate.workflow.Lane;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.workflow.WorkflowTransfer;
import domain.usecase.workflow.repository.IWorkflowRepository;

public class CreateStageUseCase {
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;
    private Lane stage;

    public CreateStageUseCase(IWorkflowRepository workflowRepository, DomainEventBus eventBus){
        this.workflowRepository = workflowRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateStageUseCaseInput input, CreateStageUseCaseOutput output) throws CloneNotSupportedException {
        Workflow workflow = WorkflowTransfer.WorkflowDTOToWorkflow(workflowRepository.getWorkflowById(input.getWorkflowId()));
        stage = workflow.createStage(input.getStageName());

//        workflow.addLane(stage);
        workflowRepository.save(WorkflowTransfer.WorkflowToWorkflowDTO(workflow));
        eventBus.postAll(workflow);

        output.setWorkflowId(stage.getWorkflowId());
        output.setStageName(stage.getLaneName());
        output.setStageId(stage.getLaneId());
    }
}
