package phd.sa.csie.ntut.edu.tw.usecase.board.create;

public class CreateBoardUseCaseOutput {
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