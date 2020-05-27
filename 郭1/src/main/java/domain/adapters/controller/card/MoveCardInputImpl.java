package domain.adapters.controller.card;

import domain.usecase.card.move.MoveCardInput;

public class MoveCardInputImpl implements MoveCardInput {
    private String fromSwimlaneId;
    private String toSwimlaneId;
    private String fromStageId;
    private String toStageId;
    private String workflowId;
    private String cardId;

    public void setFromSwimlaneId(String fromSwimlaneId) {
        this.fromSwimlaneId = fromSwimlaneId;
    }
    public void setToSwimlaneId(String toSwimlaneId) {
        this.toSwimlaneId = toSwimlaneId;
    }
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }
    public void setFromStageId(String fromStageId) {
        this.fromStageId = fromStageId;
    }
    public void setToStageId(String toStageId) {
        this.toStageId = toStageId;
    }
    public void setCardId(String cardId){
        this.cardId = cardId;
    }


    public String getFromSwimlaneId() {
        return fromSwimlaneId;
    }
    public String getToSwimlaneId() {
        return toSwimlaneId;
    }
    public String getWorkflowId() {
        return workflowId;
    }
    public String getFromStageId() {
        return fromStageId;
    }
    public String getToStageId() {
        return toStageId;
    }
    public String getCardId() {
        return cardId;
    }

}
