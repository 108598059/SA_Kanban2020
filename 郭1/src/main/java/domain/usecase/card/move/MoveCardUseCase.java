package domain.usecase.card.move;

import domain.entity.DomainEvent;
import domain.entity.DomainEventBus;
import domain.entity.FlowEvent;
import domain.entity.workflow.Swimlane;
import domain.entity.workflow.Workflow;
import domain.usecase.flowevent.FlowEventRepository;
import domain.usecase.workflow.WorkflowRepository;

public class MoveCardUseCase {
    private FlowEventRepository flowEventRepository;
    private WorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    public MoveCardUseCase(FlowEventRepository flowEventRepository, WorkflowRepository workflowRepository, DomainEventBus eventBus){
        this.workflowRepository = workflowRepository;
        this.flowEventRepository = flowEventRepository;
        this.eventBus = eventBus;
    }


    public void execute(MoveCardInput input, MoveCardOutput output) {
        Workflow workflow = workflowRepository.getWorkFlowById(input.getWorkflowId());
        workflow.moveCard(flowEventRepository, input.getFromStageId(), input.getToStageId(), input.getFromSwimlaneId(), input.getToSwimlaneId(), input.getCardId());

        workflowRepository.save(workflow);
        eventBus.postAll(workflow);

    }


}
