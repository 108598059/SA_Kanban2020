package ddd.kanban.domain.model;

import java.util.List;

public interface EventPostable {

    void addDomainEvent(DomainEvent event);

    List<DomainEvent> getDomainEvents();

    void clearDomainEvents();
}
