package ddd.kanban.domain.model;

import ddd.kanban.domain.common.DateProvider;

import java.util.Date;

public class AbstractDomainEvent implements DomainEvent {

    private final Date occurredOn;
    private final String sourceId;
    private final String id;

    public AbstractDomainEvent(String sourceId, String id) {
        super();
        this.occurredOn = DateProvider.now();
        this.sourceId = sourceId;
        this.id = id;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    @Override
    public String getSourceId() {
        return this.sourceId;
    }

}
