package domain.adapters.viewmodel.board;

import domain.adapters.View;

import java.util.ArrayList;
import java.util.List;

public class BoardViewModel {
    private List<String> boardNameList;


    public BoardViewModel() {
        this.boardNameList = new ArrayList<String>();
    }

    public void addBoard(String boardName){
        boardNameList.add(boardName);
    }

    public List<String> getBoardNameList(){
        return boardNameList;
    }
}
