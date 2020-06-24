package domain.model.service.event;

import domain.model.DomainEvent;
import domain.model.FlowEvent;
import domain.model.common.DateProvider;

import java.util.Date;
import java.util.List;

public class CycleTimeCalculated implements DomainEvent {
    private String cardId;
    private List<String> stageIds;
    private long cycleTime;
    private Date occurredOn;

    public CycleTimeCalculated(String cardId, List<String> stageIds, long cycleTime) {
        this.occurredOn = DateProvider.now();
        this.cardId = cardId;
        this.stageIds = stageIds;
        this.cycleTime = cycleTime;
    }

    public String getCardId() {
        return cardId;
    }

    public List<String> getStageIds() {
        return stageIds;
    }

    public long getCycleTime() {
        return cycleTime;
    }

    @Override
    public Date getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String detail() {
        return "CycleTimeCalculated";
    }
}
