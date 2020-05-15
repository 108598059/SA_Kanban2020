package phd.sa.csie.ntut.edu.tw.usecase.card.create;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseInput;

public class CreateCardUseCaseInput implements UseCaseInput {
  private String cardName;
  private UUID boardId;
  private UUID columnId;

  public void setCardName(String cardName) {
    this.cardName = cardName;
  }

  public UUID getColumnId() {
    return columnId;
  }

  public void setColumnId(UUID columnId) {
    this.columnId = columnId;
  }

  public UUID getBoardId() {
    return boardId;
  }

  public void setBoardId(UUID boardId) {
    this.boardId = boardId;
  }

  public String getCardName() {
    return this.cardName;
  }

}