package domain.usecase.swimlane.create;

import domain.entity.DomainEventBus;
import domain.entity.workflow.Workflow;
import domain.usecase.workflow.WorkflowRepository;

public class CreateSwimlaneUseCase {

    private WorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    public CreateSwimlaneUseCase(WorkflowRepository workflowRepository, DomainEventBus eventBus) {
        this.workflowRepository = workflowRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateSwimlaneInput createSwimlaneInput, CreateSwimlaneOutput createSwimlaneOutput) {

        Workflow workflow = workflowRepository.getWorkFlowById(createSwimlaneInput.getWorkflowId());
        String swimlaneId = workflow.createSwimlane(createSwimlaneInput.getStageId(), createSwimlaneInput.getName());

        workflowRepository.save(workflow);
        eventBus.postAll(workflow);


        createSwimlaneOutput.setSwimlaneId(swimlaneId);
    }
}
