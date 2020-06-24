package domain.entity.service;

import domain.entity.DomainEvent;
import domain.entity.DomainEventHolder;

import java.util.ArrayList;
import java.util.List;

public class Service implements DomainEventHolder {
    private List<DomainEvent> events;

    public Service(){
        this.events = new ArrayList<DomainEvent>();
    }

    public List<DomainEvent> getEvents() {
        return events;
    }

    public void addEvent(DomainEvent event){
        events.add(event);
    }

    public void clearEvents(){
        events.clear();
    }
}
