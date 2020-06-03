package phd.sa.csie.ntut.edu.tw.usecase.board.enter;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseInput;

public class EnterBoardUseCaseInput implements UseCaseInput {
    private String boardID;

    public String getBoardID() {
        return boardID;
    }

    public void setBoardID(String boardID) {
        this.boardID = boardID;
    }
}
