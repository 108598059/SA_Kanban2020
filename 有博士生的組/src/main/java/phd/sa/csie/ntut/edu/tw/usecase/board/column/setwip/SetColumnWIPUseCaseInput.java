package phd.sa.csie.ntut.edu.tw.usecase.board.column.setwip;

import java.util.UUID;

public class SetColumnWIPUseCaseInput {

  private UUID columnId;
  private int columnWIP;
  private UUID boardId;

  public void setColumnId(UUID id) {
    this.columnId = id;
  }

  public UUID getColumnId() {
    return this.columnId;
  }

  public void setBoardId(UUID id) {
    this.boardId = id;
  }

  public UUID getBoardId() {
    return this.boardId;
  }

  public void setColumnWIP(int wip) {
    this.columnWIP = wip;
  }

  public int getColumnWIP() {
    return this.columnWIP;
  }

}