package domain.model.aggregate.workflow.valueObject.flowEvent;

import domain.model.FlowEvent;
import domain.model.aggregate.workflow.valueObject.cycleTime.CycleTimeInLane;
import domain.model.common.DateProvider;

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
