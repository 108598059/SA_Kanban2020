package ddd.kanban.usecase.workflow.commit;

import ddd.kanban.domain.model.workflow.Workflow;
import ddd.kanban.usecase.repository.WorkflowRepository;

public class CommitCardUseCase {
    private WorkflowRepository workflowRepository;

    public CommitCardUseCase(WorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    public void execute(CommitCardInput commitCardInput, CommitCardOutput commitCardOutput) {
       Workflow workflow = workflowRepository.findById(commitCardInput.getWorkflowId());
       String cardId = workflow.commitCard(commitCardInput.getCardId(),
                                           commitCardInput.getLaneId());

       workflowRepository.save();

       commitCardOutput.setCardId(cardId);
    }
}
