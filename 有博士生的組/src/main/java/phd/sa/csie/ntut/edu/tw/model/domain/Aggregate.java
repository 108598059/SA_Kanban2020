package phd.sa.csie.ntut.edu.tw.model.domain;

import java.util.List;

public interface Aggregate {

    void addDomainEvent(DomainEvent event);

    List<DomainEvent> getDomainEvents();

    void clearDomainEvents();
}
