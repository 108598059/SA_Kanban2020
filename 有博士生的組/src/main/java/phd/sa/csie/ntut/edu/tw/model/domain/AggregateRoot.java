package phd.sa.csie.ntut.edu.tw.model.domain;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AggregateRoot extends Entity implements Aggregate {

    private final List<DomainEvent> domainEvents;

    public AggregateRoot() {
        super();
        domainEvents = new CopyOnWriteArrayList<>();
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
