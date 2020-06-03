package phd.sa.csie.ntut.edu.tw.usecase.column.create;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseInput;

public class CreateColumnUseCaseInput implements UseCaseInput {
    private String title;
    private String boardID;
    private int columnIndex;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setBoardID(String boardID) {
        this.boardID = boardID;
    }

    public String getBoardID() {
        return boardID;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }
}
