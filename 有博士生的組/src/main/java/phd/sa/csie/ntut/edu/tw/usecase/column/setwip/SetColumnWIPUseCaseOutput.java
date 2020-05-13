package phd.sa.csie.ntut.edu.tw.usecase.column.setwip;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;

public class SetColumnWIPUseCaseOutput implements UseCaseOutput {

  private String columnId;
  private int columnWIP;

  public void setColumnId(String id) {
    this.columnId = id;
  }

  public String getColumnId() {
    return this.columnId;
  }

  public void setColumnWIP(int wip) {
    this.columnWIP = wip;
  }

  public int getColumnWIP() {
    return this.columnWIP;
  }

}