package domain.usecase.card.cycletime;

import domain.entity.FlowEvent;
import domain.entity.workflow.Stage;
import domain.entity.workflow.Swimlane;
import domain.entity.workflow.Workflow;
import domain.usecase.flowevent.FlowEventRepository;
import domain.usecase.workflow.WorkflowRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CalculateCycleTimeUseCase {
    private String workflowId;
    private String cardId;
    private String beginningStageId;
    private String endingStageId;

    private WorkflowRepository workflowRepository;
    private FlowEventRepository flowEventRepository;
    private List<FlowEventPair> flowEventPairs;

    public CalculateCycleTimeUseCase(WorkflowRepository workflowRepository, FlowEventRepository flowEventRepository) {
        this.workflowRepository = workflowRepository;
        this.flowEventRepository = flowEventRepository;
        flowEventPairs = new ArrayList<FlowEventPair>();
    }

    public void execute(CalculateCycleTimeInput input, CalculateCycleTimeOutput output) {
        Stack<FlowEvent> stack = new Stack<FlowEvent>();
        long total = 0;
        for(FlowEvent flowEvent: flowEventRepository.getFlowEvents()) {
            if(flowEvent.getCardId().equals(input.getCardId())) {
                if(stack.empty()){
                    stack.push(flowEvent);
                }
                else {
                    FlowEvent second = stack.pop();
                    flowEventPairs.add(new FlowEventPair(second, flowEvent));
                }
            }
        }

        for(FlowEventPair flowEventPair : flowEventPairs){
            total += flowEventPair.getDiff();
        }

        output.setCycleTime(new CycleTime(total));
    }

}
