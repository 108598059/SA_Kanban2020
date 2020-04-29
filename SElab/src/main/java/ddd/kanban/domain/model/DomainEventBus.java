package ddd.kanban.domain.model;
import com.google.common.eventbus.EventBus;

public class DomainEventBus extends EventBus {
    public DomainEventBus(){
        super();
    }

//    public void postAll(AggregateRoot aggregateRoot){
//        List<DomainEvent> events =
//                new ArrayList(aggregateRoot.getDomainEvents());
//        aggregateRoot.clearDomainEvents();
//
//        for(DomainEvent each : events){
//            post(each);
//        }
//        events.clear();
//    }
}
