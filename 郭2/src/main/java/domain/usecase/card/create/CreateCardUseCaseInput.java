package domain.usecase.card.create;

public class CreateCardUseCaseInput {
    private String cardName;
    private String workflowId;
    private String laneId;
    private String cardContent;
    private String cardType;

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardName() {
        return cardName;
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

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardContent(String cardContent) {
        this.cardContent = cardContent;
    }

    public String getCardContent() {
        return cardContent;
    }
}
