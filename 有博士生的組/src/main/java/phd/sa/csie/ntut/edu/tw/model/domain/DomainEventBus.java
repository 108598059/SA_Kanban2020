package phd.sa.csie.ntut.edu.tw.model.domain;

import com.google.common.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class DomainEventBus extends EventBus {

    public DomainEventBus() {
        super();
    }

    public void postAll(Aggregate aggregate) {
        List<DomainEvent> events = new ArrayList<>(aggregate.getDomainEvents());
        aggregate.clearDomainEvents();

        for (DomainEvent each : events) {
            post(each);
        }
        events.clear();
    }
}
