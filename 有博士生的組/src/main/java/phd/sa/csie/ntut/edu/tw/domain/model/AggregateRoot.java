package phd.sa.csie.ntut.edu.tw.domain.model;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AggregateRoot extends Entity {

    private final List<DomainEvent> domainEvents;

    public AggregateRoot() {
        domainEvents = new CopyOnWriteArrayList<DomainEvent>();
    }

    public void addDomainEvent(DomainEvent event) {
        domainEvents.add(event);
    }

    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }
}
