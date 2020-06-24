package phd.sa.csie.ntut.edu.tw.usecase.cycletime;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseInput;

public class CalculateCycleTimeUseCaseInput implements UseCaseInput {
    private String cardID;
    private String startColumnID;
    private String endColumnID;

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getStartColumnID() {
        return startColumnID;
    }

    public void setStartColumnID(String startColumnID) {
        this.startColumnID = startColumnID;
    }

    public String getEndColumnID() {
        return endColumnID;
    }

    public void setEndColumnID(String endColumnID) {
        this.endColumnID = endColumnID;
    }
}
