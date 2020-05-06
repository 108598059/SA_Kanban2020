package domain.entity;

import com.google.common.eventbus.EventBus;
import domain.entity.board.Board;
import domain.entity.card.Card;
import domain.entity.workflow.Workflow;

import java.util.List;

public class DomainEventBus extends EventBus {

    public DomainEventBus(){
        super();
    }

    public void postAll(Aggregate aggregate){

        List<DomainEvent> domainEvents = aggregate.getEvents();

        for (DomainEvent event: domainEvents){
            post(event);
        }

        aggregate.clearEvents();
    }

}
