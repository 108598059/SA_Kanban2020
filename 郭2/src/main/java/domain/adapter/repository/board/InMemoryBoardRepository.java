package domain.adapter.repository.board;

import domain.usecase.board.repository.IBoardRepository;
import domain.usecase.board.BoardDTO;

import java.util.ArrayList;
import java.util.List;

public class InMemoryBoardRepository implements IBoardRepository {

    public List<BoardDTO> boardList = new ArrayList<BoardDTO>();

    public void add(BoardDTO board) {
        boardList.add(board);
    }

    public BoardDTO getBoardById(String boardId){
        for (BoardDTO each:boardList) {
            if(boardId.equals(each.getBoardId()))
                return each;
        }
        throw new RuntimeException("not found boardId = " + boardId);
    }

    public void save(BoardDTO board) {
        for (BoardDTO each : boardList) {
            if (each.getBoardId().equals(board.getBoardId())) {
                boardList.set(boardList.indexOf(each), board);
                break;
            }
        }
    }

    @Override
    public List<BoardDTO> getAllBoard() {
        return boardList;
    }

    @Override
    public String toString() {
        String boardList = "";
        for (BoardDTO board : this.boardList){
            boardList += board.getBoardName() + " ";
        }
        return boardList;
    }
}
