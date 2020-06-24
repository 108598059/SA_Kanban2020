package phd.sa.csie.ntut.edu.tw.usecase.board.move;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;

public class MoveCardUseCaseOutput implements UseCaseOutput {

    private String cardID;
    private String fromColumnID;
    private String toColumnID;

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getCardID() {
        return cardID;
    }

    public void setFromColumnID(String id) {
        this.fromColumnID = id;
    }

    public String getFromColumnID() {
        return fromColumnID;
    }

    public void setToColumnID(String id) {
        this.toColumnID = id;
    }

    public String getToColumnID() {
        return toColumnID;
    }
}