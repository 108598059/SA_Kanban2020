package domain.entity;

import com.google.common.eventbus.EventBus;
import domain.entity.aggregate.Aggregate;

import java.util.List;

public class DomainEventBus extends EventBus {

    public DomainEventBus(){
        super();
    }


    public void postAll(DomainEventHolder eventHolder){

        List<DomainEvent> domainEvents = eventHolder.getEvents();

        for (DomainEvent event: domainEvents){
            post(event);
        }

        eventHolder.clearEvents();
    }

}
