package phd.sa.csie.ntut.edu.tw.usecase.column.setwip;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseInput;

public class SetColumnWIPUseCaseInput implements UseCaseInput {

  private UUID columnID;
  private int columnWIP = 0;
  private UUID boardID;

  public void setColumnID(UUID id) {
    this.columnID = id;
  }

  public UUID getColumnID() {
    return this.columnID;
  }

  public void setBoardID(UUID id) {
    this.boardID = id;
  }

  public UUID getBoardID() {
    return this.boardID;
  }

  public void setColumnWIP(int wip) {
    this.columnWIP = wip;
  }

  public int getColumnWIP() {
    return this.columnWIP;
  }

}