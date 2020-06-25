package phd.sa.csie.ntut.edu.tw.usecase.card.create;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;

public interface CreateCardUseCaseOutput extends UseCaseOutput {

    void setCardName(String cardName);

    String getCardName();

    void setCardID(String id);

    String getCardID();
}