package kanban.domain.model.service.cycleTime;

import kanban.domain.model.FlowEvent;
import kanban.domain.model.common.DateProvider;
import kanban.domain.model.service.Service;
import kanban.domain.model.service.event.CycleTimeCalculated;

import java.util.*;

public class CycleTimeCalculater extends Service {
    private List<String> boundaryStageIds;
    private List<FlowEvent> flowEvents;
    private String cardId;
    public CycleTimeCalculater(List<String> boundaryStageIds, List<FlowEvent> flowEvents, String cardId) {
        this.boundaryStageIds = boundaryStageIds;
        this.flowEvents = flowEvents;
        this.cardId = cardId;
    }

    public long process(){
        Stack<FlowEvent> stack = new Stack<>();
        long cycleTime = 0;
        for(FlowEvent flowEvent: flowEvents) {
            if(boundaryStageIds.contains(flowEvent.getStageId())) {
                if (!flowEvent.getCardId().equals(cardId)) {
                    continue;
                }

                if (stack.empty()) {
                    // committedCard
                    stack.push(flowEvent);
                } else {
                    FlowEvent committed = stack.pop();
                    cycleTime += calculateTime(committed, flowEvent);
                }
            }
        }

        if(!stack.empty()){
            cycleTime += calculateTime(stack.pop());
        }

        addDomainEvent(new CycleTimeCalculated(cardId, boundaryStageIds, cycleTime));
        return cycleTime;
    }

    private long calculateTime(FlowEvent committed, FlowEvent uncommitted) {
        Date occurredOnOfCommitted = committed.getOccurredOn();
        Date occurredOnOfUncommitted = uncommitted.getOccurredOn();

        long diff = (occurredOnOfUncommitted.getTime() - occurredOnOfCommitted.getTime()) / 1000;
        return diff;
    }

    private long calculateTime(FlowEvent committed) {
        Date occurredOnOfCommitted = committed.getOccurredOn();
        long diff = (DateProvider.now().getTime() - occurredOnOfCommitted.getTime()) / 1000;
        return diff;
    }
}
