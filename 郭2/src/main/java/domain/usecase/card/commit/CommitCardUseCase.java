package domain.usecase.card.commit;

import domain.model.DomainEventBus;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.workflow.repository.IWorkflowRepository;

public class CommitCardUseCase {
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    public CommitCardUseCase(IWorkflowRepository workflowRepository, DomainEventBus eventBus) {
        this.eventBus = eventBus;
        this.workflowRepository = workflowRepository;
    }

    public void execute(CommitCardUseCaseInput input, CommitCardUseCaseOutput output) throws CloneNotSupportedException {
        Workflow workflow = workflowRepository.getWorkflowById(input.getWorkflowId());
        workflow.addCardInLane(input.getLaneId(), input.getCardId());

        workflowRepository.save(workflow);

        eventBus.postAll(workflow);

        output.setCardId(input.getCardId());
    }
}
