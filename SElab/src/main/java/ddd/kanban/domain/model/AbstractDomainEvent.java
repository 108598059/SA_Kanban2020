package ddd.kanban.domain.model;

import ddd.kanban.domain.model.common.DateProvider;

import java.util.Date;
import java.util.UUID;

public class AbstractDomainEvent implements  DomainEvent {

    private final Date occurredOn;
    private final String sourceId;
    private final String sourceName;
    private final String id;

    public AbstractDomainEvent(String sourceId, String sourceName, String id) {
        super();
        this.occurredOn = DateProvider.now();
        this.sourceId = sourceId;
        this.sourceName = sourceName;
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

    @Override
    public String getSourceName() {
        return this.sourceName;
    }
}
