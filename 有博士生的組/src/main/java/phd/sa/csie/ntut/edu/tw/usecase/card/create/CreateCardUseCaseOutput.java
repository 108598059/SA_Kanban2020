package phd.sa.csie.ntut.edu.tw.usecase.card.create;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;

public interface CreateCardUseCaseOutput extends UseCaseOutput {

  public void setCardName(String cardName);

  public String getCardName();

  public void setCardID(String id);

  public String getCardID();
}