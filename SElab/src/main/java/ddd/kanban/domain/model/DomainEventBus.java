package ddd.kanban.domain.model;
import com.google.common.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class DomainEventBus extends EventBus {
    public DomainEventBus(){
        super();
    }

    public void postAll(EventPostable eventPostable){
        List<DomainEvent> events =
                new ArrayList(eventPostable.getDomainEvents());
        eventPostable.clearDomainEvents();

        events.forEach(this::post);

        events.clear();
    }
}
