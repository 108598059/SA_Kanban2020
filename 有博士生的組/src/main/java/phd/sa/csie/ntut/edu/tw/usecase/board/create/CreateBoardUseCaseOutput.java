package phd.sa.csie.ntut.edu.tw.usecase.board.create;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;

public class CreateBoardUseCaseOutput implements UseCaseOutput{
  private String boardId;
  private String boardName;

  public void setBoardId(String uuid) {
    this.boardId = uuid;
  }

  public String getBoardId() {
    return this.boardId;
  }

  public void setBoardName(String title) {
    this.boardName = title;
  }

  public String getBoardName() {
    return this.boardName;
  }
}