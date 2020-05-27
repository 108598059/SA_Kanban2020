package ddd.kanban.usecase.workflow.commit;

import ddd.kanban.domain.model.workflow.Workflow;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.workflow.mapper.WorkflowEntityMapper;

public class CommitCardUseCase {
    private WorkflowRepository workflowRepository;

    public CommitCardUseCase(WorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    public void execute(CommitCardInput commitCardInput, CommitCardOutput commitCardOutput) {
       Workflow workflow = WorkflowEntityMapper.mappingWorkflowFrom(workflowRepository.findById(commitCardInput.getWorkflowId()));
       String cardId = workflow.commitCard(commitCardInput.getCardId(),
                                           commitCardInput.getLaneId());

       workflowRepository.save(WorkflowEntityMapper.mappingWorkflowEntityFrom(workflow));

       commitCardOutput.setCardId(cardId);
    }
}
