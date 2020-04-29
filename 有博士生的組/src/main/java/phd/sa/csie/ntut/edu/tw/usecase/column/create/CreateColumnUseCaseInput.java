package phd.sa.csie.ntut.edu.tw.usecase.column.create;

import java.util.UUID;

public class CreateColumnUseCaseInput {

  private String title;
  private UUID boardId;

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return this.title;
  }

  public void setBoardId(UUID boardId) {
    this.boardId = boardId;
  }

  public UUID getBoardId() {
    return this.boardId;
  }

}