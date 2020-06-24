package kanban.domain.model.aggregate;

import kanban.domain.model.DomainEvent;
import kanban.domain.model.DomainEventHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AggregateRoot implements DomainEventHolder {

    private List<DomainEvent> domainEvents;

    public AggregateRoot() {
        domainEvents = new ArrayList<>();
    }

    @Override
    public void addDomainEvent(DomainEvent event){
        domainEvents.add(event);
    }

    @Override
    public List<DomainEvent> getDomainEvents(){
        return Collections.unmodifiableList(domainEvents);
    }

    @Override
    public void clearDomainEvents(){
        domainEvents.clear();
    }
}
