package domain.model.service.cycleTime;

import domain.model.DomainEventPoster;

public class CycletimeCalculator extends DomainEventPoster {
    private String workflowId;
    private String fromLaneId;
    private String toLaneId;
    private String cardId;

    public CycletimeCalculator(String workflowId, String fromLaneId, String toLaneId, String cardId) {
        this.workflowId = workflowId;
        this.fromLaneId = fromLaneId;
        this.toLaneId = toLaneId;
        this.cardId = cardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getCardId() {
        return cardId;
    }

    public String getFromLaneId() {
        return fromLaneId;
    }

    public String getToLaneId() {
        return toLaneId;
    }
}
