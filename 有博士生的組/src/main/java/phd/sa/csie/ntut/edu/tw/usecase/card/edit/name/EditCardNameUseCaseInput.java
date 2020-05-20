package phd.sa.csie.ntut.edu.tw.usecase.card.edit.name;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseInput;

public class EditCardNameUseCaseInput implements UseCaseInput {

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