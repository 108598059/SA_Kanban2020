package phd.sa.csie.ntut.edu.tw.usecase.column.create;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;

public class CreateColumnUseCaseOutput implements UseCaseOutput {

  private String id;

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return this.id;
  }

}