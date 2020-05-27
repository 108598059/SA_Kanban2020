package domain.adapters.presenter;


import domain.adapters.viewmodel.board.BoardViewModel;

import domain.usecase.board.create.CreateBoardOutput;
import domain.adapters.View;

public class BoardPresenter implements CreateBoardOutput {


    private String boardId;
    private String boardName;

    public BoardPresenter(){
    }


    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }


    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public BoardViewModel createBoardViewModel(){
        BoardViewModel boardViewModel = new BoardViewModel();
        boardViewModel.setBoardName(this.getBoardName());
        return boardViewModel;
    }


}
