package domain.usecase.flowevent;

import domain.entity.FlowEvent;

import java.util.List;

public interface FlowEventRepository {
    public void add(FlowEvent flowEvent);
    public List<FlowEvent> getFlowEvents();
}
