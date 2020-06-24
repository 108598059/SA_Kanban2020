package ddd.kanban.usecase.card.edit;

import ddd.kanban.domain.model.card.card.CardType;

import java.util.Date;
import java.util.List;

public class EditCardUseCaseInput {
    private String workflowId;
    private String cardId;
    private String cardName;
    private String cardDescription;
    private CardType cardCardType;
    private List<String> cardTags;
    private List<String> cardAssignUsers;
    private Date cardPlannedStartDate;
    private Date cardPlannedFinishDate;
    private int cardPriority;

    public EditCardUseCaseInput(String workflowId, String cardId, String cardName, String cardDescription, CardType cardCardType, List<String> cardTags, List<String> cardAssignUsers, Date cardPlannedStartDate, Date cardPlannedFinishDate, int cardPriority) {
        this.workflowId = workflowId;
        this.cardId = cardId;
        this.cardName = cardName;
        this.cardDescription = cardDescription;
        this.cardCardType = cardCardType;
        this.cardTags = cardTags;
        this.cardAssignUsers = cardAssignUsers;
        this.cardPlannedStartDate = cardPlannedStartDate;
        this.cardPlannedFinishDate = cardPlannedFinishDate;
        this.cardPriority = cardPriority;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getCardId() {
        return cardId;
    }

    public String getCardName() {
        return cardName;
    }

    public String getCardDescription() {
        return cardDescription;
    }

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
