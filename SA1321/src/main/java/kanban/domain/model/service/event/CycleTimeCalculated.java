package kanban.domain.model.service.event;

import kanban.domain.model.DomainEvent;
import kanban.domain.model.common.DateProvider;

import java.util.Date;
import java.util.List;

public class CycleTimeCalculated implements DomainEvent {

    private Date occurredOn;
    private String cardId;
    private List<String> boundaryStageIds;

    private long cycleTime;

    public CycleTimeCalculated(
            String cardId,
            List<String> boundaryStageIds,
            long cycleTime) {
        this.occurredOn = DateProvider.now();
        this.cardId = cardId;
        this.boundaryStageIds = boundaryStageIds;
        this.cycleTime = cycleTime;
    }

    @Override
    public Date getOccurredOn() {
        return occurredOn;
    }

    public String getCardId() {
        return cardId;
    }

    public List<String> getBoundaryStageIds() {
        return boundaryStageIds;
    }

    public long getCycleTime() {
        return cycleTime;
    }
}
