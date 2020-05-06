package domain.usecase.card.create;

public class CreateCardUseCaseOutput {
    private String cardName;
    private String cardId;
    private String cardContent;
    private String cardType;

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardName() {
        return cardName;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardContent(String cardContent) {
        this.cardContent = cardContent;
    }

    public String getCardContent() {
        return cardContent;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardType() {
        return cardType;
    }
}
