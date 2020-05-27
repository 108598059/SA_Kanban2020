package ddd.kanban.usecase.card.cycleTime.calculate;

public class CalculateCycleTimeInput {
    private String cardId;
    private String workflowId;
    private String beginningLaneId;
    private String endLaneId;

    public CalculateCycleTimeInput(String cardId, String workflowId, String beginningLaneId, String endLaneId) {
        this.cardId = cardId;
        this.workflowId = workflowId;
        this.beginningLaneId = beginningLaneId;
        this.endLaneId = endLaneId;
    }

    public String getCardId() {
        return cardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getBeginningLaneId() {
        return beginningLaneId;
    }

    public String getEndLaneId() {
        return endLaneId;
    }
}
