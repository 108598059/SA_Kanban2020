package domain.usecase.card.commit;

public class CommitCardUseCaseInput {
    private String cardId;
    private String workflowId;
    private String laneId;

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setLaneId(String laneId) {
        this.laneId = laneId;
    }

    public String getLaneId() {
        return laneId;
    }
}
