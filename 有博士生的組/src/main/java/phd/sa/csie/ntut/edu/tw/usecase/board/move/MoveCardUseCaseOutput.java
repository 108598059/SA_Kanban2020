package phd.sa.csie.ntut.edu.tw.usecase.board.move;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;

public class MoveCardUseCaseOutput implements UseCaseOutput {

  private String cardID;
  private String oldColumnID;
  private String newColumnID;

  public void setCardID(String cardID) {
    this.cardID = cardID;
  }

  public String getCardID() {
    return cardID;
  }

  public void setOldColumnID(String id) {
    this.oldColumnID = id;
  }

  public String getOldColumnID() {
    return oldColumnID;
  }

  public void setNewColumnID(String id) {
    this.newColumnID = id;
  }

  public String getNewColumnID() {
    return newColumnID;
  }
}