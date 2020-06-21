package ddd.kanban.usecase.card.commit;

public class CommitCardInput {
    private String cardId;
    private String workflowId;
    private String laneId;
    private String laneTitle;

    public CommitCardInput(String cardId, String workflowId, String laneId, String laneTitle) {
        this.cardId = cardId;
        this.workflowId = workflowId;
        this.laneId = laneId;
        this.laneTitle = laneTitle;
    }

    public String getCardId() {
        return cardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getLaneId() {
        return laneId;
    }

    public String getLaneTitle() {
        return laneTitle;
    }
}
