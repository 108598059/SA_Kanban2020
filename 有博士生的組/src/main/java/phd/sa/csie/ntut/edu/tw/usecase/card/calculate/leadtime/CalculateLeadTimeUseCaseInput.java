package phd.sa.csie.ntut.edu.tw.usecase.card.calculate.leadtime;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseInput;

public class CalculateLeadTimeUseCaseInput implements UseCaseInput {
    private String cardID;

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }
}
