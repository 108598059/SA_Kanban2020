package domain.usecase.workflow.commit;

import domain.entity.workflow.Workflow;
import domain.usecase.card.CardRepository;
import domain.usecase.workflow.WorkflowRepository;

public class CommitCardUseCase {

    private WorkflowRepository workflowRepository;

    public CommitCardUseCase(WorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    public void execute(CommitCardInput commitCardInput, CommitCardOutput commitCardOutput) {
        Workflow workflow = workflowRepository.getWorkFlowById(commitCardInput.getWorkflowId());

        workflow.addCard(commitCardInput.getStageId(),commitCardInput.getSwimlaneId(),commitCardInput.getCardId());
        workflowRepository.save(workflow);

        commitCardOutput.setCardId(workflow.getCard(commitCardInput.getCardId()));
    }
}
