package ddd.kanban.domain.model;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot extends Entity implements EventPostable {

    private List<DomainEvent> domainEvents;

    private PostableEventObject postableEventObjectObject = new PostableEventObject();

    public AggregateRoot(final String id, String title) {
        super(id, title);
        domainEvents = new ArrayList<>();
    }

    public void addDomainEvent(DomainEvent event) {
        postableEventObjectObject.addDomainEvent(event);
    }

    public List<DomainEvent> getDomainEvents() {
        return postableEventObjectObject.getDomainEvents();
    }

    public void clearDomainEvents() {
        this.postableEventObjectObject.clearDomainEvents();
    }
}
