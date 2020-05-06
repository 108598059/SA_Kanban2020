package phd.sa.csie.ntut.edu.tw.domain.model;

import java.util.Date;
import java.util.UUID;

public class AbstractDomainEvent implements DomainEvent {

    private final Date occurredOn;
    private final String sourceId;
    private final String sourceName;
    private final String id;

    private Entity entity;

    private static final long serialVersionUID = 1L;

    public AbstractDomainEvent(String sourceId, String sourceName) {
        super();
        this.id = UUID.randomUUID().toString();
        this.sourceId = sourceId;
        this.occurredOn = new Date();
        this.sourceName = sourceName;
    }

    public AbstractDomainEvent(Entity entity) {
        super();
        this.entity = entity;
        this.id = UUID.randomUUID().toString();
        this.occurredOn = new Date();

        sourceId = null;
        sourceName = null;
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public int eventVersion() {
        return EventVersion.NUMBER;
    }

    @Override
    public Date occurredOn() {
        return occurredOn;
    }

    @Override
    public String detail() {
        String formatDate = String.format("occurredOn='%1$tY-%1$tm-%1$td %1$tH:%1$tM']", occurredOn());
        String format = String.format("%s[Name='%s', id='%s'] ", this.getClass().getSimpleName(), this.getSourceName(),
                this.getSourceId());
        return format + formatDate;
    }

    @Override
    public String getSourceId() {
        return sourceId;
    }

    @Override
    public String getSourceName() {
        return sourceName;
    }

    public String getId() {
        return id;
    }
}
