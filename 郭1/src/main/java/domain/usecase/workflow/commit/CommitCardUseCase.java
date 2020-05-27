package domain.usecase.workflow.commit;

import domain.entity.DomainEventBus;
import domain.entity.workflow.Workflow;
import domain.usecase.flowevent.FlowEventRepository;
import domain.usecase.workflow.WorkflowRepository;

public class CommitCardUseCase {
    private FlowEventRepository flowEventRepository;
    private WorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    public CommitCardUseCase(FlowEventRepository flowEventRepository, WorkflowRepository workflowRepository, DomainEventBus eventBus) {
        this.workflowRepository = workflowRepository;
        this.flowEventRepository = flowEventRepository;
        this.eventBus = eventBus;
    }

    public void execute(CommitCardInput commitCardInput, CommitCardOutput commitCardOutput) {
        Workflow workflow = workflowRepository.getWorkFlowById(commitCardInput.getWorkflowId());

        workflow.addCard(flowEventRepository, commitCardInput.getStageId(),commitCardInput.getSwimlaneId(),commitCardInput.getCardId());
        workflowRepository.save(workflow);
        eventBus.postAll(workflow);

        commitCardOutput.setCardId(commitCardInput.getCardId());
    }
}
