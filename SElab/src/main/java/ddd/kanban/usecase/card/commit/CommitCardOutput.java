package ddd.kanban.usecase.card.commit;

public class CommitCardOutput {
    private String cardId;
    private String workflowId;
    private String ColumnId;

    public CommitCardOutput() {
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getColumnId() {
        return ColumnId;
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

    public void setColumnId(String columnId) {
        this.ColumnId = columnId;
    }
}
