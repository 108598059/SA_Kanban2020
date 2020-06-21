package ddd.kanban.domain.model.reporting;

import ddd.kanban.domain.model.DomainEvent;
import ddd.kanban.domain.model.PostableEventObject;
import ddd.kanban.domain.model.EventPostable;

import java.util.List;

public class CycleTimeCalculator implements EventPostable {

    private PostableEventObject postableEventObjectObject = new PostableEventObject();
    @Override
    public void addDomainEvent(DomainEvent event) {
        postableEventObjectObject.addDomainEvent(event);
    }

    @Override
    public List<DomainEvent> getDomainEvents() {
        return postableEventObjectObject.getDomainEvents();
    }

    @Override
    public void clearDomainEvents() {
        this.postableEventObjectObject.clearDomainEvents();
    }

    //TODO 俊凱
}
