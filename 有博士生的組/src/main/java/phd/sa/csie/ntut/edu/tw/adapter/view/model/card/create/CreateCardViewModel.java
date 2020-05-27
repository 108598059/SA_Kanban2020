package phd.sa.csie.ntut.edu.tw.adapter.view.model.card.create;

import phd.sa.csie.ntut.edu.tw.adapter.view.model.ViewModelStatus;

public class CreateCardViewModel {
    private String cardID;
    private String cardName;
    private ViewModelStatus status;

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public ViewModelStatus getStatus() {
        return status;
    }

    public void setStatus(ViewModelStatus status) {
        this.status = status;
    }
}
