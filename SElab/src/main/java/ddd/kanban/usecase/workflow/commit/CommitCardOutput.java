package ddd.kanban.usecase.workflow.commit;

public class CommitCardOutput {
    private String cardId;
    private String workflowId;
    private String laneId;

    public CommitCardOutput() {
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getLaneId() {
        return laneId;
    }

    public String getCardId() {
        return cardId;
    }


    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public void setLaneId(String laneId) {
        this.laneId = laneId;
    }
}
