package ddd.kanban.usecase.card.commit;

public class CommitCardInput {
    private String cardId;
    private String workflowId;
    private String columnId;

    public CommitCardInput(String cardId, String workflowId, String columnId) {
        this.cardId = cardId;
        this.workflowId = workflowId;
        this.columnId = columnId;
    }

    public String getCardId() {
        return cardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getColumnId() {
        return columnId;
    }

}
