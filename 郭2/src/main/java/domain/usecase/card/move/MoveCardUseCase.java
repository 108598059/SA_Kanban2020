package domain.usecase.card.move;

import domain.model.aggregate.DomainEventBus;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.workflow.repository.IWorkflowRepository;

public class MoveCardUseCase {
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    public MoveCardUseCase(IWorkflowRepository workflowRepository, DomainEventBus eventBus) {
        this.workflowRepository = workflowRepository;
        this.eventBus = eventBus;
    }


    public void execute(MoveCardUseCaseInput input, MoveCardUseCaseOutput output) {
        Workflow workflow = workflowRepository.getWorkflowById(input.getWorkflowId());

        workflow.moveCard(input.getCardId(), input.getFromLaneId(), input.getToLaneId());

        workflowRepository.save(workflow);
        eventBus.postAll(workflow);

        output.setWorkflowId(workflow.getWorkflowId());
    }
}
