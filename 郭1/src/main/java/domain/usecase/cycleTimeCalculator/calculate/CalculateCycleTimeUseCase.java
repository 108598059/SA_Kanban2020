package domain.usecase.cycleTimeCalculator.calculate;

import domain.entity.DomainEventBus;
import domain.entity.service.cycleTime.CycleTimeCalculator;
import domain.usecase.cycleTimeCalculator.FlowEventPair;
import domain.usecase.flowevent.FlowEventRepository;
import domain.usecase.workflow.WorkflowRepository;

public class CalculateCycleTimeUseCase {
    private String workflowId;
    private String cardId;
    private String beginningStageId;
    private String endingStageId;

    private WorkflowRepository workflowRepository;
    private FlowEventRepository flowEventRepository;
    private FlowEventPair flowEventPair;
    private DomainEventBus eventBus ;

    public CalculateCycleTimeUseCase(WorkflowRepository workflowRepository, FlowEventRepository flowEventRepository, DomainEventBus eventBus) {
        this.workflowRepository = workflowRepository;
        this.flowEventRepository = flowEventRepository;
        this.eventBus = eventBus ;
    }

    public void execute(CalculateCycleTimeInput input, CalculateCycleTimeOutput output) {

        CycleTimeCalculator cycleTimeCalculator = new CycleTimeCalculator() ;
        output.setCycleTime( cycleTimeCalculator.execute(input.getFromStageId(),input.getFromSwimlaneId(),input.getToStageId(),input.getToSwimlaneId(), input.getCardId(), flowEventRepository) );
        eventBus.postAll(cycleTimeCalculator);
    }
    private boolean bothEqul(String inputStage, String inputSwimlane, String flowEventStage, String flowEventSwimlane){
        if(inputStage.equals(flowEventStage) && inputSwimlane.equals(flowEventSwimlane)) return true;
        return false;
    }

}
