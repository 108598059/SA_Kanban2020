package domain.controller;

import domain.usecase.workflow.commit.CommitCardOutput;

public class CommitCardOutputImpl implements CommitCardOutput {

    private String cardId;

    public void setCardId(String cardId){
        this.cardId = cardId;
    }

    public String getCardId() {
        return cardId;
    }
}
