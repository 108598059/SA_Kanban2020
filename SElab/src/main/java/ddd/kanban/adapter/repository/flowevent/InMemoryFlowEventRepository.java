package ddd.kanban.adapter.repository.flowevent;

import ddd.kanban.domain.model.FlowEvent;
import ddd.kanban.usecase.repository.FlowEventRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryFlowEventRepository implements FlowEventRepository {

    private List<FlowEvent> flowEvents;

    public InMemoryFlowEventRepository() {
        flowEvents = new ArrayList<>();
    }

    @Override
    public void add(FlowEvent flowEvent) {
        flowEvents.add(flowEvent);
    }

    @Override
    public void save(FlowEvent flowEvent) {
        //TODO
    }

    @Override
    public List<FlowEvent> findAll() {
        return this.flowEvents;
    }
}
