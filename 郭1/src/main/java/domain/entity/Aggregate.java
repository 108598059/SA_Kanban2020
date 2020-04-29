package domain.entity;

import java.util.ArrayList;
import java.util.List;

public class Aggregate {
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
