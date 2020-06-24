package domain.entity.service.cycleTime;

import domain.entity.FlowEvent;
import domain.entity.service.Service;
import domain.entity.service.cycleTime.event.CycleTimeCalculated;
import domain.usecase.cycleTimeCalculator.CycleTime;
import domain.usecase.cycleTimeCalculator.FlowEventPair;
import domain.usecase.flowevent.FlowEventRepository;

import java.util.Stack;

public class CycleTimeCalculator extends Service {

    public CycleTimeCalculator() {
        super();
    }

    public CycleTime execute(String fromStageId , String fromSwimlaneId , String toStageId , String toSwimlaneId, String CardId, FlowEventRepository flowEventRepository) {
        String inputFrom = fromStageId + fromSwimlaneId;
        String inputTo = toStageId + toSwimlaneId;
        FlowEventPair flowEventPair = null;

        Stack<FlowEvent> stack = new Stack<FlowEvent>();
        long total = 0;
        int i=0;
        for(FlowEvent flowEvent: flowEventRepository.getFlowEvents()) {

            String flowEventFrom = flowEvent.getFromStageId() + flowEvent.getFromSwimlaneId();
            String flowEventTo = flowEvent.getToStageId() + flowEvent.getToSwimlaneId();

            System.out.println(i++);
            if(flowEvent.getCardId().equals(CardId)) {
                if(inputFrom.equals(flowEventFrom) || inputFrom.equals(flowEventTo) || inputTo.equals(flowEventFrom) || inputTo.equals(flowEventTo)){
                    if(stack.empty()){
                        stack.push(flowEvent);
                        System.out.println("pushed");
                        System.out.println(flowEvent.getCardId() + " : " + flowEvent.getOccurredOn());
                    }
                    else if(inputTo.equals(flowEventTo)){

                        flowEventPair= new FlowEventPair(stack.pop(), flowEvent);
                        System.out.println("popped");
                        System.out.println(flowEvent.getCardId() + " : " + flowEvent.getOccurredOn());
                        break;
                    }
                }
            }
        }

        addEvent(new CycleTimeCalculated(fromStageId ,fromSwimlaneId ,toStageId ,toSwimlaneId, CardId));
        return new CycleTime(flowEventPair.getDiff()) ;
    }

}
