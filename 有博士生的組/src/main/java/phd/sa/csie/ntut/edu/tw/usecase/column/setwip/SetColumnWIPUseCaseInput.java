package phd.sa.csie.ntut.edu.tw.usecase.column.setwip;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseInput;

public class SetColumnWIPUseCaseInput implements UseCaseInput {

  private String columnID;
  private int columnWIP = 0;
  private String boardID;

  public void setColumnID(String id) {
    this.columnID = id;
  }

  public String getColumnID() {
    return this.columnID;
  }

  public void setBoardID(String id) {
    this.boardID = id;
  }

  public String getBoardID() {
    return this.boardID;
  }

  public void setColumnWIP(int wip) {
    this.columnWIP = wip;
  }

  public int getColumnWIP() {
    return this.columnWIP;
  }

}