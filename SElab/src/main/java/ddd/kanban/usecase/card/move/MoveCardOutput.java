package ddd.kanban.usecase.card.move;

public class MoveCardOutput {
    private String toColumnId;
    private String cardId;

    public String getToColumnId() {
        return toColumnId;
    }

    public void setToColumnId(String toColumnId) {
        this.toColumnId = toColumnId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
