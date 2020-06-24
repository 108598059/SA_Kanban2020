package ddd.kanban.usecase.card.move;

public class MoveCardInput {
    private final String workflowId;
    private final String fromColumnId;
    private final String toColumnId;
    private final String cardId;

    public MoveCardInput(String workflowId, String fromColumnId, String toColumnId, String cardId) {
        this.workflowId = workflowId;
        this.fromColumnId = fromColumnId;
        this.toColumnId = toColumnId;
        this.cardId = cardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getFromColumnId() {
        return fromColumnId;
    }

    public String getToColumnId() {
        return toColumnId;
    }

    public String getCardId() {
        return cardId;
    }
}
