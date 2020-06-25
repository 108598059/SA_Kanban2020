package phd.sa.csie.ntut.edu.tw.usecase.card.edit.name;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;

public class EditCardNameUseCaseOutput implements UseCaseOutput {
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