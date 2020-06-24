package domain.model.service.cycleTime;

import domain.model.DomainEventHolder;
import domain.model.FlowEvent;
import domain.model.common.DateProvider;
import domain.model.service.event.CycleTimeCalculated;

import java.util.Date;
import java.util.List;
import java.util.Stack;

public class CycleTimeCalculator extends DomainEventHolder {
    private List<String> laneIds;
    private List<FlowEvent> flowEvents;
    private String cardId;

    public CycleTimeCalculator(List<String> laneIds, List<FlowEvent> flowEvents, String cardId) {
        this.laneIds = laneIds;
        this.flowEvents = flowEvents;
        this.cardId = cardId;
    }

    public long process() {
        Stack<FlowEvent> stack = new Stack<>();
        long cycleTime = 0;
        for(FlowEvent flowEvent: flowEvents) {
            if(laneIds.contains(flowEvent.getLaneId())) {
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

        addDomainEvent(new CycleTimeCalculated(cardId, laneIds, cycleTime));
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
