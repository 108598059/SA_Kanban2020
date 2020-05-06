package ddd.kanban.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AggregateRoot extends Entity {

    private List<DomainEvent> domainEvents;

    public AggregateRoot(final String id, String title) {
        super(id, title);
        domainEvents = new ArrayList<>();
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
