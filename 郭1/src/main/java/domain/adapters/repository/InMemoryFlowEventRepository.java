package domain.adapters.repository;

import domain.entity.FlowEvent;
import domain.usecase.flowevent.FlowEventRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryFlowEventRepository implements FlowEventRepository {

    private List<FlowEvent> flowEvents = new ArrayList<FlowEvent>();


    public void add(FlowEvent flowEvent) {
        flowEvents.add(flowEvent);
    }

    public List<FlowEvent> getFlowEvents() {
        return flowEvents;
    }
}
