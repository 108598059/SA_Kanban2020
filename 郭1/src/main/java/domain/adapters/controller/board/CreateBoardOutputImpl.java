package domain.adapters.controller.board;

import domain.usecase.board.create.CreateBoardOutput;

public class CreateBoardOutputImpl implements CreateBoardOutput {
    private String boardId;
    private String boardName;

    public String getBoardId() {
        return boardId;
    }


    public void setBoardId(String boardId){
        this.boardId = boardId;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardName() {
        return boardName;
    }
}
