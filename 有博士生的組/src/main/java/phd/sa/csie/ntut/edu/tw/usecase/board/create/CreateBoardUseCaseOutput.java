package phd.sa.csie.ntut.edu.tw.usecase.board.create;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;

public class CreateBoardUseCaseOutput implements UseCaseOutput{
  private String boardID;
  private String boardTitle;

  public void setBoardId(String uuid) {
    this.boardID = uuid;
  }

  public String getBoardId() {
    return this.boardID;
  }

  public void setBoardTitle(String title) {
    this.boardTitle = title;
  }

  public String getBoardTitle() {
    return this.boardTitle;
  }
}