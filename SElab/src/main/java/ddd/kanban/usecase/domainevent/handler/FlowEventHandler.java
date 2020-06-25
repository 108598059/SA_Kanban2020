package ddd.kanban.usecase.domainevent.handler;

import com.google.common.eventbus.Subscribe;
import ddd.kanban.domain.model.FlowEvent;
import ddd.kanban.usecase.repository.FlowEventRepository;

public class FlowEventHandler {
    private FlowEventRepository flowEventRepository;

    public FlowEventHandler(FlowEventRepository flowEventRepository){
        this.flowEventRepository = flowEventRepository;
    }

    @Subscribe
    public void handleFlowEvent(FlowEvent flowEvent){
        flowEventRepository.add(flowEvent);
    }
}
