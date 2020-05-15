package phd.sa.csie.ntut.edu.tw.usecase.board.create;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;

public class CreateBoardUseCaseOutput implements UseCaseOutput{
  private String boardID;
  private String boardName;

  public void setBoardId(String uuid) {
    this.boardID = uuid;
  }

  public String getBoardId() {
    return this.boardID;
  }

  public void setBoardName(String boardName) {
    this.boardName = boardName;
  }

  public String getBoardName() {
    return this.boardName;
  }
}