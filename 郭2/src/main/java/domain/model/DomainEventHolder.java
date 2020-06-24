package domain.model;

import domain.model.DomainEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DomainEventHolder {

    private List<DomainEvent> domainEvents;

    public DomainEventHolder() {
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
