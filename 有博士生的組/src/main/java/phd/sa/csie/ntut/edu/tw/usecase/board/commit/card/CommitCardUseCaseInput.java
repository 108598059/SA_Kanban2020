package phd.sa.csie.ntut.edu.tw.usecase.board.commit.card;

public class CommitCardUseCaseInput {
    private String boardID;
    private String cardID;

    public String getBoardID() {
        return boardID;
    }

    public void setBoardID(String boardID) {
        this.boardID = boardID;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }
}
