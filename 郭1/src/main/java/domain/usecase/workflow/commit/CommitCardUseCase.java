package domain.usecase.workflow.commit;

import domain.entity.DomainEventBus;
import domain.entity.aggregate.workflow.Workflow;
import domain.usecase.flowevent.FlowEventRepository;
import domain.usecase.workflow.WorkflowRepository;
import domain.usecase.workflow.WorkflowTransformer;

public class CommitCardUseCase {
    private WorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    public CommitCardUseCase( WorkflowRepository workflowRepository, DomainEventBus eventBus) {
        this.workflowRepository = workflowRepository;
        this.eventBus = eventBus;
    }

    public void execute(CommitCardInput commitCardInput, CommitCardOutput commitCardOutput) {
        Workflow workflow = WorkflowTransformer.toWorkflow(workflowRepository.getWorkFlowById(commitCardInput.getWorkflowId()));

        workflow.addCard(commitCardInput.getStageId(),commitCardInput.getSwimlaneId(),commitCardInput.getCardId());
        workflowRepository.save(WorkflowTransformer.toDTO(workflow));
        eventBus.postAll(workflow);
        commitCardOutput.setCardId(commitCardInput.getCardId());
    }
}
