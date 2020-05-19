package phd.sa.csie.ntut.edu.tw.usecase.board.move;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseInput;

public class MoveCardUseCaseInput implements UseCaseInput {

  private UUID boardID;
  private UUID cardID;
  private UUID fromColumnID;
  private UUID toColumnID;

  public void setBoardID(UUID boardID) {
    this.boardID = boardID;
  }

  public UUID getBoardID() {
    return this.boardID;
  }

  public void setCardID(UUID cardID) {
    this.cardID = cardID;
  }

  public UUID getCardID() {
    return this.cardID;
  }

  public void setFromColumnID(UUID id) {
    this.fromColumnID = id;
  }

  public UUID getFromColumnID() {
    return this.fromColumnID;
  }

  public void setToColumnID(UUID id) {
    this.toColumnID = id;
  }

  public UUID getToColumnID() {
    return this.toColumnID;
  }

}