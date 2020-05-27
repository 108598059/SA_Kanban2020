package phd.sa.csie.ntut.edu.tw.usecase.card.edit.name;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseInput;

public class EditCardNameUseCaseInput implements UseCaseInput {

    private String cardID;
    private String cardName;

    public void setCardID(String id) {
        this.cardID = id;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardName() {
        return cardName;
    }

}