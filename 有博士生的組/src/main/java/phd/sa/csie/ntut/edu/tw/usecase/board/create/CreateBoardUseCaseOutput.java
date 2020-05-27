package phd.sa.csie.ntut.edu.tw.usecase.board.create;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;

public interface CreateBoardUseCaseOutput extends UseCaseOutput {
    public void setBoardID(String boardID);

    public String getBoardID();

    public void setBoardName(String boardName);

    public String getBoardName();
}