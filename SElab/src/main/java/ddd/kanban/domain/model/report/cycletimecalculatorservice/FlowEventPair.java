package ddd.kanban.domain.model.report.cycletimecalculatorservice;

import ddd.kanban.domain.common.DateProvider;
import ddd.kanban.domain.model.FlowEvent;

public class FlowEventPair {
    private FlowEvent committed;
    private FlowEvent uncommitted;

    public FlowEventPair(FlowEvent committed, FlowEvent uncommitted) {
        this.committed = committed;
        this.uncommitted = uncommitted;
    }

    public String getColumnId() {
        return committed.getSourceId();
    }

    public CycleTime getCycleTime() {
        return new CycleTime(((uncommitted == null) ? DateProvider.now() : uncommitted.occurredOn()).getTime() - committed.occurredOn().getTime());
    }
}
