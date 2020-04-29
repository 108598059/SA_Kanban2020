package domain.adapter.repository.board;

import domain.model.aggregate.board.Board;
import domain.usecase.board.repository.IBoardRepository;

import java.util.ArrayList;

public class InMemoryBoardRepository implements IBoardRepository {

    private ArrayList<Board> boardList = new ArrayList<Board>();

    public void add(Board board) {
        boardList.add(board);
    }

    public Board getBoardById(String boardId){
        for (Board each:boardList) {
            if(boardId.equals(each.getBoardId()))
                return each;
        }
        throw new RuntimeException("not found boardId = " + boardId);
    }

    public void save(Board board) {
        for (Board each : boardList) {
            if (each.getBoardId().equals(board.getBoardId())) {
                boardList.set(boardList.indexOf(each), board);
                break;
            }
        }
    }
}
