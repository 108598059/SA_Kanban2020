package phd.sa.csie.ntut.edu.tw.usecase.column.create;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;

public class CreateColumnUseCaseOutput implements UseCaseOutput {

  private String id;

  public void setID(String id) {
    this.id = id;
  }

  public String getID() {
    return this.id;
  }

}