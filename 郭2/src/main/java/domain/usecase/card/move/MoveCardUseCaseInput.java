package domain.usecase.card.move;

public class MoveCardUseCaseInput {
    private String workflowId;
    private String fromLaneId;
    private String toLaneId;
    private String cardId;

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setFromLaneId(String fromLaneId) {
        this.fromLaneId = fromLaneId;
    }

    public String getFromLaneId() {
        return fromLaneId;
    }

    public void setToLaneId(String toLaneId) {
        this.toLaneId = toLaneId;
    }

    public String getToLaneId() {
        return toLaneId;
    }
}
