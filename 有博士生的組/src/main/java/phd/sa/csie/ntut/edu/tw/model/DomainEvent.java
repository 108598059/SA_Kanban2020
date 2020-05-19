package phd.sa.csie.ntut.edu.tw.model;

import java.util.Date;

public interface DomainEvent {
    int eventVersion();

    Date occurredOn();

    String detail();

    public String getSourceID();

    public String getSourceName();
}
