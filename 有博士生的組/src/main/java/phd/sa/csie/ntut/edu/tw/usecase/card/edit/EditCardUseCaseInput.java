package phd.sa.csie.ntut.edu.tw.usecase.card.edit;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseInput;

public class EditCardUseCaseInput implements UseCaseInput {

  private UUID cardID;
  private String cardName;

  public void setCardID(UUID id) {
    this.cardID = id;
  }

  public UUID getCardID() {
    return this.cardID;
  }

  public void setCardName(String cardName) {
    this.cardName = cardName;
  }

  public String getCardName() {
    return this.cardName;
  }

}