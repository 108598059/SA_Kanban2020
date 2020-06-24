package domain.usecase.workflow.create;

import domain.entity.DomainEventBus;
import domain.entity.aggregate.workflow.Workflow;
import domain.usecase.workflow.WorkflowRepository;
import domain.usecase.workflow.WorkflowTransformer;

public class CreateSwimlaneUseCase {

    private WorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    public CreateSwimlaneUseCase(WorkflowRepository workflowRepository, DomainEventBus eventBus) {
        this.workflowRepository = workflowRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateSwimlaneInput createSwimlaneInput, CreateSwimlaneOutput createSwimlaneOutput) {

        Workflow workflow = WorkflowTransformer.toWorkflow(workflowRepository.getWorkFlowById(createSwimlaneInput.getWorkflowId()));
        String swimlaneId = workflow.createSwimlane(createSwimlaneInput.getStageId(), createSwimlaneInput.getName());

        workflowRepository.save(WorkflowTransformer.toDTO(workflow));
        eventBus.postAll(workflow);


        createSwimlaneOutput.setSwimlaneId(swimlaneId);
    }
}
