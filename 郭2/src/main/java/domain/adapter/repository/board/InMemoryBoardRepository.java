package domain.adapter.repository.board;

import domain.model.aggregate.board.Board;
import domain.usecase.board.repository.IBoardRepository;
import domain.usecase.entity.BoardEntity;

import java.util.ArrayList;

public class InMemoryBoardRepository implements IBoardRepository {

    private ArrayList<BoardEntity> boardList = new ArrayList<BoardEntity>();

    public void add(BoardEntity board) {
        boardList.add(board);
    }

    public BoardEntity getBoardById(String boardId){
        for (BoardEntity each:boardList) {
            if(boardId.equals(each.getBoardId()))
                return each;
        }
        throw new RuntimeException("not found boardId = " + boardId);
    }

    public void save(BoardEntity board) {
        for (BoardEntity each : boardList) {
            if (each.getBoardId().equals(board.getBoardId())) {
                boardList.set(boardList.indexOf(each), board);
                break;
            }
        }
    }
}
