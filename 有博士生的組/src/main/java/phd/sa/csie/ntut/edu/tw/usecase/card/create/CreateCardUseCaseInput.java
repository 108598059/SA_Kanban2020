package phd.sa.csie.ntut.edu.tw.usecase.card.create;

public class CreateCardUseCaseInput {
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