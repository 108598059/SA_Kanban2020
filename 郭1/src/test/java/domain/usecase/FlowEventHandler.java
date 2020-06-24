package domain.usecase;

import com.google.common.eventbus.Subscribe;
import domain.entity.DomainEventBus;
import domain.entity.aggregate.workflow.event.CardCommitted;
import domain.entity.aggregate.workflow.event.CardMoved;
import domain.usecase.flowevent.FlowEventRepository;

public class FlowEventHandler {
    private FlowEventRepository flowEventRepository;
    public FlowEventHandler(FlowEventRepository flowEventRepository){
        this.flowEventRepository = flowEventRepository;
    }

    @Subscribe
    public void addFlowEvent(CardMoved cardMoved){
        flowEventRepository.add(cardMoved);
    }
    @Subscribe
    public void addFlowEvent(CardCommitted cardCommitted){
        flowEventRepository.add(cardCommitted);
    }
}
