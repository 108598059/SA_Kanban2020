package domain.model;

import com.google.common.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class DomainEventBus extends EventBus {

    public DomainEventBus(){
        super();
    }

    public void postAll(DomainEventPoster domainEventPoster){
        List<DomainEvent> events =
                new ArrayList(domainEventPoster.getDomainEvents());
        domainEventPoster.clearDomainEvents();

        for(DomainEvent each : events){
            post(each);
        }
        events.clear();
    }
}
