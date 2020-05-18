package phd.sa.csie.ntut.edu.tw.usecase.column.create;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseInput;

public class CreateColumnUseCaseInput implements UseCaseInput {

  private String title;
  private UUID boardID;

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return this.title;
  }

  public void setBoardID(UUID boardID) {
    this.boardID = boardID;
  }

  public UUID getBoardID() {
    return this.boardID;
  }

}