package tw.teddysoft.clean.domain.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AggregateRoot extends Entity {

    private static final long serialVersionUID = 1L;
    private final List<DomainEvent> domainEvents;

    public AggregateRoot(String name) {
        super(name);
        domainEvents = new CopyOnWriteArrayList<>();
    }
    public void addDomainEvent(DomainEvent event){
        domainEvents.add(event);
    }
    public List<DomainEvent> getDomainEvents(){
        return Collections.unmodifiableList(domainEvents);
    }
    public void clearDomainEvents(){
        domainEvents.clear();
    }
}

