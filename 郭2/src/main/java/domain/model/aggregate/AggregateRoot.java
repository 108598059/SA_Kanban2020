package domain.model.aggregate;

import domain.model.DomainEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AggregateRoot {

    private List<DomainEvent> domainEvents;

    public AggregateRoot() {
        domainEvents = new ArrayList<DomainEvent>();
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
