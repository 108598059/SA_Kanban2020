package ddd.kanban.usecase.card.cycleTime.calculate;

import ddd.kanban.domain.model.FlowEvent;
import ddd.kanban.domain.model.common.DateProvider;

public class FlowEventPair {
    private FlowEvent committed;
    private FlowEvent uncommitted;

    public FlowEventPair(FlowEvent committed, FlowEvent uncommitted) {
        this.committed = committed;
        this.uncommitted = uncommitted;
    }

    public String getLaneId() {
        return committed.getSourceId();
    }

    public CycleTime getCycleTime() {
        return new CycleTime(((uncommitted == null) ? DateProvider.now():uncommitted.occurredOn()).getTime() - committed.occurredOn().getTime());
    }
}
