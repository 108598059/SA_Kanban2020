package phd.sa.csie.ntut.edu.tw.usecase.cycletime;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseInput;

public class CalculateCycleTimeUseCaseInput implements UseCaseInput {
    private String cardID;
    private int startColumnIndex;
    private int endColumnIndex;

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public int getStartColumnIndex() {
        return startColumnIndex;
    }

    public void setStartColumnIndex(int startColumnIndex) {
        this.startColumnIndex = startColumnIndex;
    }

    public int getEndColumnIndex() {
        return endColumnIndex;
    }

    public void setEndColumnIndex(int endColumnIndex) {
        this.endColumnIndex = endColumnIndex;
    }
}
