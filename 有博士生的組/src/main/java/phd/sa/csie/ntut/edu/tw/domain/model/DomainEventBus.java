package phd.sa.csie.ntut.edu.tw.domain.model;

import com.google.common.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class DomainEventBus extends EventBus {

    public DomainEventBus() {
        super();
    }

    public void postAll(AggregateRoot aggregateRoot) {
        List<DomainEvent> events = new ArrayList<DomainEvent>(aggregateRoot.getDomainEvents());
        aggregateRoot.clearDomainEvents();

        for (DomainEvent each : events) {
            post(each);
        }
        events.clear();
    }
}
