package phd.sa.csie.ntut.edu.tw.usecase.column.read;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseInput;

public class GetColumnsByBoardIDUsecaseInput implements UseCaseInput {
    private String boardId;

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }
}
