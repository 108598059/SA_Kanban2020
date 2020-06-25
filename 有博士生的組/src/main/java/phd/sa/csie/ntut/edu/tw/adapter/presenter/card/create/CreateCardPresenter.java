package phd.sa.csie.ntut.edu.tw.adapter.presenter.card.create;

import phd.sa.csie.ntut.edu.tw.adapter.view.model.card.create.CreateCardViewModel;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseOutput;

public class CreateCardPresenter implements CreateCardUseCaseOutput {
    private String cardID;
    private String cardName;

    public CreateCardViewModel build() {
        CreateCardViewModel viewModel = new CreateCardViewModel();
        viewModel.setCardID(this.cardID);
        viewModel.setCardName(this.cardName);
        return viewModel;
    }

    @Override
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    @Override
    public String getCardName() {
        return cardName;
    }

    @Override
    public void setCardID(String id) {
        this.cardID = id;
    }

    @Override
    public String getCardID() {
        return cardID;
    }
}