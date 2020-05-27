package phd.sa.csie.ntut.edu.tw.usecase.card.edit.column;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;

public class EditCardBelongsColumnUseCaseOutput implements UseCaseOutput {
    private String columnID;

    public String getColumnID() {
        return columnID;
    }

    public void setColumnID(String columnID) {
        this.columnID = columnID;
    }
}
