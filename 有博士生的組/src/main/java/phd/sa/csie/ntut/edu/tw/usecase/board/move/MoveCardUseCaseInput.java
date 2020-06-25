package phd.sa.csie.ntut.edu.tw.usecase.board.move;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseInput;

public class MoveCardUseCaseInput implements UseCaseInput {

    private String boardID;
    private String cardID;
    private String fromColumnID;
    private String toColumnID;

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

    public String getFromColumnID() {
        return fromColumnID;
    }

    public void setFromColumnID(String fromColumnID) {
        this.fromColumnID = fromColumnID;
    }

    public String getToColumnID() {
        return toColumnID;
    }

    public void setToColumnID(String toColumnID) {
        this.toColumnID = toColumnID;
    }
}

