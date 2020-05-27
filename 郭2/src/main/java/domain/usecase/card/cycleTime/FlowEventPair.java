package domain.usecase.card.cycleTime;

import domain.model.FlowEvent;
import domain.model.common.DateProvider;
import domain.model.valueObject.CycleTimeInLane;

import java.util.Date;

public class FlowEventPair {
    private CycleTimeInLane cycleTimeInLane;
    public FlowEventPair(FlowEvent committed){
        String stageId = committed.getLaneId();
        Date occurredOnOfCommitted = committed.getOccurredOn();
        long diff = (DateProvider.now().getTime() - occurredOnOfCommitted.getTime()) / 1000;
        cycleTimeInLane = new CycleTimeInLane(stageId, diff);
    }
    public FlowEventPair(FlowEvent committed, FlowEvent uncommitted) {
        String stageId = committed.getLaneId();
        Date occurredOnOfCommitted = committed.getOccurredOn();
        Date occurredOnOfUncommitted = uncommitted.getOccurredOn();

        long diff = (occurredOnOfUncommitted.getTime() - occurredOnOfCommitted.getTime()) / 1000;

        cycleTimeInLane = new CycleTimeInLane(stageId, diff);
    }

    public CycleTimeInLane getCycleTimeInLane() {
        return cycleTimeInLane;
    }
}
