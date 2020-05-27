package domain.adapter.repository.flowEvent;

import domain.model.FlowEvent;
import domain.usecase.flowEvent.repository.IFlowEventRepository;

import java.util.ArrayList;
import java.util.List;

public class ImMemoryFlowEventRepository implements IFlowEventRepository {
    List<FlowEvent> flowEventList = new ArrayList<FlowEvent>();

    @Override
    public void add(FlowEvent flowEvent) {
        flowEventList.add(flowEvent);
    }

    @Override
    public List<FlowEvent> getAll() {
        return flowEventList;
    }
}
