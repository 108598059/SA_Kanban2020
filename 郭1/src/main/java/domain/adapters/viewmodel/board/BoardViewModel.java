package domain.adapters.viewmodel.board;

import domain.adapters.View;

import java.util.ArrayList;
import java.util.List;

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
