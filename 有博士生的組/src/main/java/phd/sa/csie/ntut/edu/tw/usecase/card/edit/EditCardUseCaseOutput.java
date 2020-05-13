package phd.sa.csie.ntut.edu.tw.usecase.card.edit;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;

public class EditCardUseCaseOutput implements UseCaseOutput {
  private String cardId;
  private String cardName;

  public void setCardId(String id) {
    this.cardId = id;
  }

  public String getCardId() {
    return this.cardId;
  }

  public void setCardName(String cardName) {
    this.cardName = cardName;
  }

  public String getCardName() {
    return this.cardName;
  }
}