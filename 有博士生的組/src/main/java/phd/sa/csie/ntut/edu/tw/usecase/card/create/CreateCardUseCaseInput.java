package phd.sa.csie.ntut.edu.tw.usecase.card.create;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseInput;

public class CreateCardUseCaseInput implements UseCaseInput {
  private String cardName;
  private String boardID;

  public void setCardName(String cardName) {
    this.cardName = cardName;
  }

  public String getBoardID() {
    return boardID;
  }

  public void setBoardID(String boardID) {
    this.boardID = boardID;
  }

  public String getCardName() {
    return this.cardName;
  }
}