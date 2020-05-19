package phd.sa.csie.ntut.edu.tw.usecase.card.edit;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;

public class EditCardUseCaseOutput implements UseCaseOutput {
  private String cardID;
  private String cardName;

  public void setCardID(String id) {
    this.cardID = id;
  }

  public String getCardID() {
    return this.cardID;
  }

  public void setCardName(String cardName) {
    this.cardName = cardName;
  }

  public String getCardName() {
    return this.cardName;
  }
}