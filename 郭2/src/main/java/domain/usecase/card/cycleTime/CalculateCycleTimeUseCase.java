package domain.usecase.card.cycleTime;

import domain.model.aggregate.workflow.Lane;
import domain.model.aggregate.workflow.Workflow;
import domain.model.FlowEvent;
import domain.model.aggregate.workflow.valueObject.cycleTime.CycleTime;
import domain.model.aggregate.workflow.valueObject.flowEvent.FlowEventPair;
import domain.usecase.flowEvent.repository.IFlowEventRepository;
import domain.usecase.workflow.repository.IWorkflowRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CalculateCycleTimeUseCase {
    private IWorkflowRepository workflowRepository;
    private IFlowEventRepository flowEventRepository;
    private List<FlowEventPair> flowEventPairList;

    public CalculateCycleTimeUseCase(IWorkflowRepository workflowRepository, IFlowEventRepository flowEventRepository) {
        this.flowEventRepository = flowEventRepository;
        this.workflowRepository = workflowRepository;
        flowEventPairList = new ArrayList<FlowEventPair>();
    }

    public void execute(CalculateCycleTimeInput input, CalculateCycleTimeOutput output) {
        Stack<FlowEvent> stack = new Stack<>();

        for(FlowEvent flowEvent : flowEventRepository.getAll()) {
            if(!flowEvent.getCardId().equals(input.getCardId())) {
                continue;
            }
            if(stack.empty()) {
                // committedCard
                stack.push(flowEvent);
            }else {
                FlowEvent committed = stack.pop();
                flowEventPairList.add(new FlowEventPair(committed, flowEvent));
            }
        }

//        if(!stack.empty()){
//            flowEventPairList.add(new FlowEventPair(stack.pop()));
//        }

        Workflow workflow = workflowRepository.getWorkflowById(input.getWorkflowId());
        boolean isbool = false;
        List<String> stageIds = new ArrayList();
        for(Lane lane: workflow.getLaneList()){
            if(lane.getLaneId().equals(input.getFromLaneId())){
                isbool = true;
            }else if(lane.getLaneId().equals(input.getToLaneId())){
                stageIds.add(lane.getLaneId());
                break;
            }
            if(isbool) {
                stageIds.add(lane.getLaneId());
            }

        }

        long time = 0;
        for(String stageId: stageIds){
            for(FlowEventPair flowEventPair : flowEventPairList){
                if(flowEventPair.getCycleTimeInLane().getLaneId().equals(stageId)){
                    time += flowEventPair.getCycleTimeInLane().getDiff();
                }
            }
        }

        //System.out.println(time);
        CycleTime cycleTime = new CycleTime(time);

        output.setCycleTime(cycleTime);
    }
}
