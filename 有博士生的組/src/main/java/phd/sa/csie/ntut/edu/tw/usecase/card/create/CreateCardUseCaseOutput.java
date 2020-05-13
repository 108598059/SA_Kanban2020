package phd.sa.csie.ntut.edu.tw.usecase.card.create;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;

public class CreateCardUseCaseOutput implements UseCaseOutput {
  private String cardId;
  private String cardName;

  public void setCardName(String cardName) {
    this.cardName = cardName;
  }

  public String getCardName() {
    return this.cardName;
  }

  public void setCardId(UUID uuid) {
    this.cardId = uuid.toString();
  }

  public String getCardId() {
    return this.cardId;
  }
}