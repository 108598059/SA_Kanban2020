package domain.entity.service.cycleTime.event;

import domain.entity.DomainEvent;

public class CycleTimeCalculated implements DomainEvent {
    private String cardId ;
    private String fromStageId ;
    private String toStageId ;
    private String fromSwimlaneId ;
    private String toSwimlaneId ;


    public CycleTimeCalculated(String cardId, String fromStageId, String toStageId, String fromSwimlaneId, String toSwimlaneId) {
        this.cardId = cardId;
        this.fromStageId = fromStageId;
        this.toStageId = toStageId;
        this.fromSwimlaneId = fromSwimlaneId;
        this.toSwimlaneId = toSwimlaneId;
    }

    public String getCardId() {
        return cardId;
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
}
