package phd.sa.csie.ntut.edu.tw.usecase.card.move;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseInput;

public class MoveCardUseCaseInput implements UseCaseInput {

  private UUID boardId;
  private UUID cardId;
  private UUID fromColumnId;
  private UUID toColumnId;

  public void setBoardId(UUID boardId) {
    this.boardId = boardId;
  }

  public UUID getBoardId() {
    return this.boardId;
  }

  public void setCardId(UUID cardId) {
    this.cardId = cardId;
  }

  public UUID getCardId() {
    return this.cardId;
  }

  public void setFromColumnId(UUID id) {
    this.fromColumnId = id;
  }

  public UUID getFromColumnId() {
    return this.fromColumnId;
  }

  public void setToColumnId(UUID id) {
    this.toColumnId = id;
  }

  public UUID getToColumnId() {
    return this.toColumnId;
  }

}