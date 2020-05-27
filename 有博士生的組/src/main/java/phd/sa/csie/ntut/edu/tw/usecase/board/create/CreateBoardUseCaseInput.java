package phd.sa.csie.ntut.edu.tw.usecase.board.create;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseInput;

public class CreateBoardUseCaseInput implements UseCaseInput {
  private String boardName;
  private String workspaceID;

  public void setBoardName(String boardName) {
    this.boardName = boardName;
  }

  public String getBoardName() {
    return boardName;
  }

  public String getWorkspaceID() {
    return workspaceID;
  }

  public void setWorkspaceID(String workspaceID) {
    this.workspaceID = workspaceID;
  }
}