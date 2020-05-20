package phd.sa.csie.ntut.edu.tw.usecase.card.create;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;

public class CreateCardUseCaseOutput implements UseCaseOutput {
  private String cardID;
  private String cardName;

  public void setCardName(String cardName) {
    this.cardName = cardName;
  }

  public String getCardName() {
    return this.cardName;
  }

  public void setCardID(String id) {
    this.cardID = id;
  }

  public String getCardID() {
    return this.cardID;
  }
}