package ddd.kanban.domain.model;

import java.util.Date;

public interface DomainEvent {
    public Date occurredOn();

    public String getSourceId();
}
