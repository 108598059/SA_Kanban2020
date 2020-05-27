package domain.usecase.flowEvent.repository;

import domain.model.FlowEvent;

import java.util.List;

public interface IFlowEventRepository {
    void add(FlowEvent flowEvent);

    List<FlowEvent> getAll();
}
