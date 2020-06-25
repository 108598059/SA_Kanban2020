package ddd.kanban.domain.model.report.cycletimecalculatorservice;

import ddd.kanban.domain.model.*;
import ddd.kanban.domain.model.report.cycletimecalculatorservice.event.CycleTimeCalculated;

import java.util.List;
import java.util.UUID;

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

    public CycleTime process(String cardId, List<FlowEventPair> flowEventPairs, List<String> columnIntervalIds) {
        long diff = flowEventPairs.stream()
                                    .filter(flowEventPair -> columnIntervalIds.contains(flowEventPair.getColumnId()))
                                    .mapToLong(flowEventPair -> flowEventPair.getCycleTime().getMillisecond())
                                    .sum();
        CycleTime cycleTime = new CycleTime(diff);
        addDomainEvent(new CycleTimeCalculated(cardId, "", columnIntervalIds, cycleTime, UUID.randomUUID().toString()));
        return cycleTime;
    }
}
