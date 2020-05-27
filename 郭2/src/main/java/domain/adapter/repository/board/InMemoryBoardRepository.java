package domain.adapter.repository.board;

import domain.usecase.board.repository.IBoardRepository;
import domain.usecase.board.BoardEntity;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Default;
import java.util.ArrayList;
import java.util.List;
@Alternative
public class InMemoryBoardRepository implements IBoardRepository {

    public List<BoardEntity> boardList = new ArrayList<BoardEntity>();

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

    @Override
    public String toString() {
        String boardList = "";
        for (BoardEntity board : this.boardList){
            boardList += board.getBoardName() + " ";
        }
        return boardList;
    }
}
