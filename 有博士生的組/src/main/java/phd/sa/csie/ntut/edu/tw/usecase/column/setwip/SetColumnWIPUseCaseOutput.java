package phd.sa.csie.ntut.edu.tw.usecase.column.setwip;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;

public class SetColumnWIPUseCaseOutput implements UseCaseOutput {

    private String columnID;
    private int columnWIP;

    public void setColumnID(String id) {
        this.columnID = id;
    }

    public String getColumnID() {
        return columnID;
    }

    public void setColumnWIP(int wip) {
        this.columnWIP = wip;
    }

    public int getColumnWIP() {
        return columnWIP;
    }

}