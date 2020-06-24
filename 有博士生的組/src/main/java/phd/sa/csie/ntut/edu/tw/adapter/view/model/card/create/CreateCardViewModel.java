package phd.sa.csie.ntut.edu.tw.adapter.view.model.card.create;

import phd.sa.csie.ntut.edu.tw.adapter.view.model.AbstractViewModel;

public class CreateCardViewModel extends AbstractViewModel {
    private String cardID;
    private String cardName;

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
}
