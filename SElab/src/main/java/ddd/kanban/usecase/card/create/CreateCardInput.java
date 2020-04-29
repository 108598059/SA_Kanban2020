package ddd.kanban.usecase.card.create;

import ddd.kanban.domain.model.card.CardType;

import java.util.Date;
import java.util.List;

public class CreateCardInput {

    private String cardTitle;
    private String cardDescription;
    private CardType cardCardType;
    private List<String> cardTags;
    private List<String> cardAssignUsers;
    private Date cardPlannedStartDate;
    private Date cardPlannedFinishDate;
    private int cardPriority;

    public CreateCardInput(String cardTitle, String cardDescription, CardType cardType, List<String> tags, List<String>assignUsers, Date plannedStartDate, Date plannedFinishDate, int priority){
        this.cardTitle = cardTitle;
        this.cardDescription = cardDescription;
        this.cardCardType = cardType;
        this.cardTags = tags;
        this.cardAssignUsers = assignUsers;
        this.cardPlannedStartDate = plannedStartDate;
        this.cardPlannedFinishDate = plannedFinishDate;
        this.cardPriority = priority;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public String getCardDescription() { return cardDescription; }

    public CardType getCardCardType() {
        return cardCardType;
    }

    public List<String> getCardTags() {
        return cardTags;
    }

    public List<String> getCardAssignUsers() {
        return cardAssignUsers;
    }

    public Date getCardPlannedStartDate() {
        return cardPlannedStartDate;
    }

    public Date getCardPlannedFinishDate() {
        return cardPlannedFinishDate;
    }

    public int getCardPriority() {
        return cardPriority;
    }

}
