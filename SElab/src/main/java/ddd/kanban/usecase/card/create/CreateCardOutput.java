package ddd.kanban.usecase.card.create;

import ddd.kanban.domain.model.card.CardType;

import java.util.Date;
import java.util.List;

public class CreateCardOutput {
    private String cardId;
    private String cardTitle;
    private String cardDescription;
    private CardType cardCardType;
    private List<String> cardTags;
    private List<String> cardAssignUsers;
    private Date cardPlannedStartDate;
    private Date cardPlannedFinishDate;
    private int cardPriority;

    public CreateCardOutput(){

    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public void setCardDescription(String cardDescription) {
        this.cardDescription = cardDescription;
    }

    public CardType getCardCardType() {
        return cardCardType;
    }

    public void setCardCardType(CardType cardCardType) {
        this.cardCardType = cardCardType;
    }

    public List<String> getCardTags() {
        return cardTags;
    }

    public void setCardTags(List<String> cardTags) {
        this.cardTags = cardTags;
    }

    public List<String> getCardAssignUsers() {
        return cardAssignUsers;
    }

    public void setCardAssignUsers(List<String> cardAssignUsers) {
        this.cardAssignUsers = cardAssignUsers;
    }

    public Date getCardPlannedStartDate() {
        return cardPlannedStartDate;
    }

    public void setCardPlannedStartDate(Date cardPlannedStartDate) {
        this.cardPlannedStartDate = cardPlannedStartDate;
    }

    public Date getCardPlannedFinishDate() {
        return cardPlannedFinishDate;
    }

    public void setCardPlannedFinishDate(Date cardPlannedFinishDate) {
        this.cardPlannedFinishDate = cardPlannedFinishDate;
    }

    public int getCardPriority() {
        return cardPriority;
    }

    public void setCardPriority(int cardPriority) {
        this.cardPriority = cardPriority;
    }

}
