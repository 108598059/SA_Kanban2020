package domain.usecase.card.cycletime;

import domain.entity.FlowEvent;

public class FlowEventPair {
    private CycleTime cycleTime;
    private long diff;
    public FlowEventPair(FlowEvent first, FlowEvent second) {
        this.diff = second.getOccurredOn().getTime() - first.getOccurredOn().getTime();
        this.cycleTime = new CycleTime(second.getOccurredOn().getTime() - first.getOccurredOn().getTime());
    }
    public CycleTime getCycleTime(){
        return cycleTime;
    }
    public long getDiff(){
        return diff / 1000;
    }
}
