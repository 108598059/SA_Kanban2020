package domain.model.aggregate;

import com.google.common.eventbus.EventBus;
import domain.model.DomainEvent;

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

        for(DomainEvent each : events){
            post(each);
        }
        events.clear();
    }
}
