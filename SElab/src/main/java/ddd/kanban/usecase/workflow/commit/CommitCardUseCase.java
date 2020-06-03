package ddd.kanban.usecase.workflow.commit;

import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.workflow.Workflow;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.workflow.mapper.WorkflowEntityMapper;

public class CommitCardUseCase {
    private WorkflowRepository workflowRepository;
    private DomainEventBus domainEventBus;

    public CommitCardUseCase(WorkflowRepository workflowRepository,DomainEventBus domainEventBus) {
        this.workflowRepository = workflowRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(CommitCardInput commitCardInput, CommitCardOutput commitCardOutput) {
       Workflow workflow = WorkflowEntityMapper.mappingWorkflowFrom(workflowRepository.findById(commitCardInput.getWorkflowId()));
       String cardId = workflow.commitCard(commitCardInput.getCardId(),
                                           commitCardInput.getLaneId());

       workflowRepository.save(WorkflowEntityMapper.mappingWorkflowEntityFrom(workflow));
       domainEventBus.postAll(workflow);
       commitCardOutput.setCardId(cardId);
    }
}
