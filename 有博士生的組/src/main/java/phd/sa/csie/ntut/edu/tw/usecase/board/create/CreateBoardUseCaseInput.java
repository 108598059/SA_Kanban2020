package phd.sa.csie.ntut.edu.tw.usecase.board.create;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseInput;

public class CreateBoardUseCaseInput implements UseCaseInput {
  private String title;

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return this.title;
  }
}