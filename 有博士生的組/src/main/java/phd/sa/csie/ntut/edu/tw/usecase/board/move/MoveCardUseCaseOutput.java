package phd.sa.csie.ntut.edu.tw.usecase.board.move;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;

public class MoveCardUseCaseOutput implements UseCaseOutput {

  private String cardID;
  private String oldColumnID;
  private String newColumnID;

  public void setCardID(String cardID) {
    this.cardID = cardID;
  }

  public String getCardID() {
    return this.cardID;
  }

  public void setOldColumnID(String id) {
    this.oldColumnID = id;
  }

  public String getOldColumnID() {
    return this.oldColumnID;
  }

  public void setNewColumnID(String id) {
    this.newColumnID = id;
  }

  public String getNewColumnID() {
    return this.newColumnID;
  }
}