package phd.sa.csie.ntut.edu.tw.model;

import phd.sa.csie.ntut.edu.tw.model.common.DateProvider;

import java.util.Date;
import java.util.UUID;

public class AbstractDomainEvent implements DomainEvent {

    private final Date occurredOn;
    private final String sourceID;
    private final String sourceName;
    private final String id;

    private Entity entity;

    public AbstractDomainEvent(String sourceID, String sourceName) {
        super();
        this.id = UUID.randomUUID().toString();
        this.sourceID = sourceID;
        this.occurredOn = DateProvider.now();
        this.sourceName = sourceName;
    }

    public AbstractDomainEvent(String sourceID, String sourceName, Entity entity) {
        super();
        this.entity = entity;
        this.id = UUID.randomUUID().toString();
        this.occurredOn = DateProvider.now();

        this.sourceID = sourceID;
        this.sourceName = sourceName;
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
                this.getSourceID());
        return format + formatDate;
    }

    @Override
    public String getSourceID() {
        return sourceID;
    }

    @Override
    public String getSourceName() {
        return sourceName;
    }

    public String getID() {
        return id;
    }
}
