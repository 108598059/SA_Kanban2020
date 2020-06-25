package phd.sa.csie.ntut.edu.tw.usecase.column.setwip;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseInput;

public class SetColumnWIPUseCaseInput implements UseCaseInput {

    private String columnID;
    private int columnWIP;
    private String boardID;

    public void setColumnID(String id) {
        this.columnID = id;
    }

    public String getColumnID() {
        return columnID;
    }

    public void setBoardID(String id) {
        this.boardID = id;
    }

    public String getBoardID() {
        return boardID;
    }

    public void setColumnWIP(int wip) {
        this.columnWIP = wip;
    }

    public int getColumnWIP() {
        return columnWIP;
    }

}