package domain.adapters.viewmodel.board;

public class BoardViewModel {
    private String boardName;


    public BoardViewModel() {

    }

    public void setBoardName(String boardName){
        this.boardName = boardName;
    }

    public String getBoardName(){
        return boardName;
    }
}
