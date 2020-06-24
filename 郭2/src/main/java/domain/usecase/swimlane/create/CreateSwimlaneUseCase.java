package domain.usecase.swimlane.create;

import domain.model.DomainEventBus;
import domain.model.aggregate.workflow.Lane;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.workflow.WorkflowTransfer;
import domain.usecase.workflow.repository.IWorkflowRepository;

public class CreateSwimlaneUseCase {
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    public CreateSwimlaneUseCase(IWorkflowRepository workflowRepository, DomainEventBus eventBus) {
        this.workflowRepository = workflowRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateSwimlaneUseCaseInput input, CreateSwimlaneUseCaseOutput output) throws CloneNotSupportedException {
        Workflow workflow = WorkflowTransfer.WorkflowDTOToWorkflow(workflowRepository.getWorkflowById(input.getWorkflowId()));
        Lane swimlane = workflow.createSwimlane(input.getSwimlaneName());

//        workflow.addLane(swimlane);
        workflowRepository.save(WorkflowTransfer.WorkflowToWorkflowDTO(workflow));
        eventBus.postAll(workflow);

        output.setWorkflowId(swimlane.getWorkflowId());
        output.setSwimlaneName(swimlane.getLaneName());
        output.setSwimlaneId(swimlane.getLaneId());
    }
}
