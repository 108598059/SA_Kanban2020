package ddd.kanban.domain.model;
import com.google.common.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class DomainEventBus extends EventBus {
    public DomainEventBus(){
        super();
    }

    public void postAll(AggregateRoot aggregateRoot){
        List<DomainEvent> events =
                new ArrayList(aggregateRoot.getDomainEvents());
        aggregateRoot.clearDomainEvents();

        events.forEach(this::post);

        events.clear();
    }
}
