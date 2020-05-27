package phd.sa.csie.ntut.edu.tw.adapter.view.model.board.create;

import phd.sa.csie.ntut.edu.tw.adapter.view.model.AbstractViewModel;

public class CreateBoardViewModel extends AbstractViewModel {
    private String boardID;
    private String boardName;

    public void setBoardID(String boardID) {
        this.boardID = boardID;
    }

    public String getBoardID() {
        return this.boardID;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardName() {
        return this.boardName;
    }
}
