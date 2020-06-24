package domain.entity;

import java.util.ArrayList;
import java.util.List;

public interface DomainEventHolder {


    public List<DomainEvent> getEvents() ;

    public void addEvent(DomainEvent event);

    public void clearEvents();
}
