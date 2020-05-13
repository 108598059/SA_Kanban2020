package phd.sa.csie.ntut.edu.tw.usecase.card.edit;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseInput;

public class EditCardUseCaseInput implements UseCaseInput {

  private UUID cardId;
  private String cardName;

  public void setCardId(UUID id) {
    this.cardId = id;
  }

  public UUID getCardId() {
    return this.cardId;
  }

  public void setCardName(String cardName) {
    this.cardName = cardName;
  }

  public String getCardName() {
    return this.cardName;
  }

}