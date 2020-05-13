package phd.sa.csie.ntut.edu.tw.usecase.card.move;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;

public class MoveCardUseCaseOutput implements UseCaseOutput {

  private String cardId;
  private String oldColumnId;
  private String newColumnId;

  public void setCardId(UUID cardId) {
    this.cardId = cardId.toString();
  }

  public String getCardId() {
    return this.cardId;
  }

  public void setOldColumnId(String id) {
    this.oldColumnId = id;
  }

  public String getOldColumnId() {
    return this.oldColumnId;
  }

  public void setNewColumnId(String id) {
    this.newColumnId = id;
  }

  public String getNewColumnId() {
    return this.newColumnId;
  }
}