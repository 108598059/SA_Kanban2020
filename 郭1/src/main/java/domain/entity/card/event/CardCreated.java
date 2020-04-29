package domain.entity.card.event;

import domain.entity.DomainEvent;

public class CardCreated extends DomainEvent {

    private String workflowId;
    private String stageId;
    private String swimlaneId;
    private String cardId;

    public CardCreated(String workflowId, String stageId, String swimlaneId, String cardId) {
        this.workflowId = workflowId;
        this.stageId = stageId;
        this.swimlaneId = swimlaneId;
        this.cardId = cardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getStageId() {
        return stageId;
    }

    public String getSwimlaneId() {
        return swimlaneId;
    }

    public String getCardId() {
        return cardId;
    }
}
