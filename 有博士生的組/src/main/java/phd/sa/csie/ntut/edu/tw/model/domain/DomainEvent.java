package phd.sa.csie.ntut.edu.tw.model.domain;

import java.util.Date;

public interface DomainEvent {
    int eventVersion();

    Date occurredOn();

    String detail();

    String getSourceID();

    String getSourceName();
}
