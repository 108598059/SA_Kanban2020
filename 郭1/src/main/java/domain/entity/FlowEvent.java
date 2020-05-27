package domain.entity;



import domain.common.DateProvider;

import java.util.Date;

public class FlowEvent implements DomainEvent {
    private Date occurredOn;

    private String fromStageId;
    private String toStageId;
    private String fromSwimlaneId;
    private String toSwimlaneId;
    private String cardId;

    public FlowEvent(String fromStageId, String toStageId, String fromSwimlaneId, String toSwimlaneId, String cardId) {
        this.fromStageId = fromStageId;
        this.toStageId = toStageId;
        this.fromSwimlaneId = fromSwimlaneId;
        this.toSwimlaneId = toSwimlaneId;
        this.cardId = cardId;
        this.occurredOn = DateProvider.now();
    }
    
    public Date getOccurredOn() {
        return occurredOn;
    }

    public String getFromStageId() {
        return fromStageId;
    }

    public String getToStageId() {
        return toStageId;
    }

    public String getFromSwimlaneId() {
        return fromSwimlaneId;
    }

    public String getToSwimlaneId() {
        return toSwimlaneId;
    }

    public String getCardId() {
        return cardId;
    }

}

