package domain.entity.aggregate;

import domain.entity.DomainEvent;
import domain.entity.DomainEventHolder;

import java.util.ArrayList;
import java.util.List;

public class Aggregate implements DomainEventHolder {
    private List<DomainEvent> events;

    public Aggregate(){
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
