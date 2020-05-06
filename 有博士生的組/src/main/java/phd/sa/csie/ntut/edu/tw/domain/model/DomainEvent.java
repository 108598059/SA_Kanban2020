package phd.sa.csie.ntut.edu.tw.domain.model;

import java.util.Date;

public interface DomainEvent {
    int eventVersion();

    Date occurredOn();

    String detail();

    public String getSourceId();

    public String getSourceName();
}
