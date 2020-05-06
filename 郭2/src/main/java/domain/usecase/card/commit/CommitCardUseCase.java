package domain.usecase.card.commit;

import domain.model.aggregate.workflow.Lane;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.workflow.repository.IWorkflowRepository;

public class CommitCardUseCase {
    private IWorkflowRepository workflowRepository;

    public CommitCardUseCase(IWorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    public void execute(CommitCardUseCaseInput input, CommitCardUseCaseOutput output) {
        Workflow workflow = workflowRepository.getWorkflowById(input.getWorkflowId());
        workflow.addCardInLane(input.getLaneId(), input.getCardId());

        workflowRepository.save(workflow);

        output.setCardId(input.getCardId());
    }
}
