package ddd.kanban.usecase.card.move;

public class MoveCardInput {
    private final String workflowId;
    private final String fromLaneId;
    private final String toLaneId;
    private final String cardId;

    public MoveCardInput(String workflowId, String fromLaneId, String toLaneId, String cardId) {
        this.workflowId = workflowId;
        this.fromLaneId = fromLaneId;
        this.toLaneId = toLaneId;
        this.cardId = cardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getFromLaneId() {
        return fromLaneId;
    }

    public String getToLaneId() {
        return toLaneId;
    }

    public String getCardId() {
        return cardId;
    }
}
