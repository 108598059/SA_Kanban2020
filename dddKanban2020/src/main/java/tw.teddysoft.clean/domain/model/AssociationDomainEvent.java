package tw.teddysoft.clean.domain.model;

import tw.teddysoft.clean.domain.common.DateProvider;

import java.util.Date;
import java.util.UUID;

public class AssociationDomainEvent extends AbstractDomainEvent {

    private final String id;
    private final Date occurredOn;
    private final String containerId;
    private final String containeeId;

    private static final long serialVersionUID = 1L;

    public AssociationDomainEvent(String containerId, String containeeId) {
        super(containerId, containeeId);
        this.id = UUID.randomUUID().toString();
        this.occurredOn = DateProvider.now();
        this.containerId = containerId;
        this.containeeId = containeeId;
    }

    public AssociationDomainEvent(Entity entity) {
        super(entity);

        this.id = UUID.randomUUID().toString();
        this.occurredOn = DateProvider.now();
        this.containerId = null;
        this.containeeId = null;
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
        String format = String.format(
                "%s[container id='%s', containee id='%s'] ",
                this.getClass().getSimpleName(),
                this.getContainerId(), this.getContaineeId());
        return format + formatDate;
    }

    public String getId() {
        return id;
    }

    protected String getContainerId() {
        return containerId;
    }

    protected String getContaineeId() {
        return containeeId;
    }
}
