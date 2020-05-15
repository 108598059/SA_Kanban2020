package phd.sa.csie.ntut.edu.tw.usecase.column.read;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseInput;

public class GetColumnsByBoardIDUsecaseInput implements UseCaseInput {
    private String boardID;

    public String getBoardID() {
        return boardID;
    }

    public void setBoardID(String boardID) {
        this.boardID = boardID;
    }
}
