package domain.usecase.handler.domainEvent;

import com.google.common.eventbus.Subscribe;
import domain.model.FlowEvent;
import domain.usecase.flowEvent.repository.IFlowEventRepository;

public class FlowEventHandler {
    private IFlowEventRepository flowEventRepository;

    public FlowEventHandler(IFlowEventRepository flowEventRepository) {
        this.flowEventRepository = flowEventRepository;

    }

    @Subscribe
    public void handleEvent(FlowEvent flowEvent) {
        flowEventRepository.add(flowEvent);
    }
}
