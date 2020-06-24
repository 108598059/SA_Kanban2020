package domain.usecase.workflow.create;

import domain.model.DomainEventBus;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.workflow.repository.IWorkflowRepository;

public class CreateWorkflowUseCase {
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    public CreateWorkflowUseCase(IWorkflowRepository workflowRepository, DomainEventBus eventBus) {
        this.eventBus = eventBus;
        this.workflowRepository = workflowRepository;
    }

    public void execute(CreateWorkflowUseCaseInput input, CreateWorkflowUseCaseOutput output) {
        Workflow workflow = new Workflow(input.getWorkflowName(), input.getBoardId());

        workflowRepository.add(workflow);

        eventBus.postAll(workflow);

        output.setWorkflowId(workflow.getWorkflowId());
        output.setWorkflowName(workflow.getWorkflowName());
    }
}
