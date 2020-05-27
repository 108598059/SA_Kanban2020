package ddd.kanban.usecase.workflow.move;

public class MoveCardOutput {
    private String toLaneId;
    private String cardId;

    public String getToLaneId() {
        return toLaneId;
    }

    public void setToLaneId(String toLaneId) {
        this.toLaneId = toLaneId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
