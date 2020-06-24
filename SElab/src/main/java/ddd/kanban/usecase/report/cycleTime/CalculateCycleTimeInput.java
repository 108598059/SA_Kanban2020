package ddd.kanban.usecase.report.cycleTime;

public class CalculateCycleTimeInput {
    private String cardId;
    private String workflowId;
    private String beginningColumnId;
    private String endColumnId;

    public CalculateCycleTimeInput(String cardId, String workflowId, String beginningColumnId, String endColumnId) {
        this.cardId = cardId;
        this.workflowId = workflowId;
        this.beginningColumnId = beginningColumnId;
        this.endColumnId = endColumnId;
    }

    public String getCardId() {
        return cardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getBeginningColumnId() {
        return beginningColumnId;
    }

    public String getEndColumnId() {
        return endColumnId;
    }
}
