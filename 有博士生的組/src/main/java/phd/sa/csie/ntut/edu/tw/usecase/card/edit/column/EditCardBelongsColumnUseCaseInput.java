package phd.sa.csie.ntut.edu.tw.usecase.card.edit.column;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseInput;

public class EditCardBelongsColumnUseCaseInput implements UseCaseInput {
    private String cardID;
    private String columnID;

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getColumnID() {
        return columnID;
    }

    public void setColumnID(String columnID) {
        this.columnID = columnID;
    }
}
