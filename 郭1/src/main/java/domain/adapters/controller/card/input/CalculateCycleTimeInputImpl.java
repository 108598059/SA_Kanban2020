package domain.adapters.controller.card.input;

import domain.usecase.cycleTimeCalculator.calculate.CalculateCycleTimeInput;

public class CalculateCycleTimeInputImpl implements CalculateCycleTimeInput {
    private String workflowId;
    private String cardId;
    private String fromStageId;
    private String toStageId;
    private String fromSwimlaneId;
    private String toSwimlaneId;


    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
    public void setFromStageId(String fromStageId) {
        this.fromStageId = fromStageId;
    }
    public void setToStageId(String toStageId) {
        this.toStageId = toStageId;
    }
    public void setFromSwimlane(String fromSwimlaneId) {
        this.fromSwimlaneId = fromSwimlaneId;
    }
    public void setToSwimlane(String toSwimlaneId) {
        this.toSwimlaneId = toSwimlaneId;
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
    public String getFromStageId() {
        return fromStageId;
    }
    public String getCardId() {
        return cardId;
    }
    public String getWorkflowId() {
        return workflowId;
    }
}
