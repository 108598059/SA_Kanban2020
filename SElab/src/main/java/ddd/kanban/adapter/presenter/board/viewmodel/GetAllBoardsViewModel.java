package ddd.kanban.adapter.presenter.board.viewmodel;

import java.util.List;

public class GetAllBoardsViewModel {
    private String boardId;
    private String boardTitle;
    private String boardDescription;


    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }


    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }


    public void setBoardDescription(String boardDescription) {
        this.boardDescription = boardDescription;
    }


    public String getBoardId() {
        return this.boardId;
    }


    public String getBoardTitle() {
        return this.boardTitle;
    }


    public String getBoardDescription() {
        return this.boardDescription;
    }
}
