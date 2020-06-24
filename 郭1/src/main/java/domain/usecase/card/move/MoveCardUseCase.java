package domain.usecase.card.move;

import domain.entity.DomainEventBus;
import domain.entity.aggregate.workflow.Workflow;
import domain.usecase.flowevent.FlowEventRepository;
import domain.usecase.workflow.WorkflowRepository;
import domain.usecase.workflow.WorkflowTransformer;

public class MoveCardUseCase {
    private WorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    public MoveCardUseCase(WorkflowRepository workflowRepository, DomainEventBus eventBus){
        this.workflowRepository = workflowRepository;
        this.eventBus = eventBus;
    }


    public void execute(MoveCardInput input, MoveCardOutput output) {
        Workflow workflow = WorkflowTransformer.toWorkflow(workflowRepository.getWorkFlowById(input.getWorkflowId()));
        workflow.moveCard( input.getFromStageId(), input.getToStageId(), input.getFromSwimlaneId(), input.getToSwimlaneId(), input.getCardId());

        workflowRepository.save(WorkflowTransformer.toDTO(workflow));
        eventBus.postAll(workflow);

    }


}
