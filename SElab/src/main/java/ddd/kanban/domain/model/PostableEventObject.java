package ddd.kanban.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PostableEventObject implements EventPostable {
    private List<DomainEvent> events;

    public PostableEventObject(){
        events = new ArrayList<>();
    }

    @Override
    public void addDomainEvent(DomainEvent event) {
        events.add(event);
    }

    @Override
    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(events);
    }

    @Override
    public void clearDomainEvents() {
        events.clear();
    }
}
