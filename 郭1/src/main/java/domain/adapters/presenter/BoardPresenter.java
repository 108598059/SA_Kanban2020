package domain.adapters.presenter;


import domain.usecase.board.create.CreateBoardOutput;
import domain.adapters.View;

public class BoardPresenter implements CreateBoardOutput {

    private View view;
    private String boardId;
    private String boardName;

    public BoardPresenter(View view){
        this.view = view;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
        drawBoard();
    }


    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    private void drawBoard(){
        view.addBoard(this.boardName);
    }
}
