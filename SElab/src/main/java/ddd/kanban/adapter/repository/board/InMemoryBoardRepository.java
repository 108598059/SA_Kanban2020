package ddd.kanban.adapter.repository.board;

import ddd.kanban.domain.model.Board.Board;
import ddd.kanban.usecase.repository.BoardRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class InMemoryBoardRepository implements BoardRepository {
    private List<Board> boards;

    public InMemoryBoardRepository(){
        boards = new ArrayList<Board>();
    }


    @Override
    public void add(Board board) {
        boards.add(board);
    }

    @Override
    public Board findById(String boardId) {
        return boards.stream()
                .filter(findBoardById(boardId))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private static Predicate<Board> findBoardById(String boardId){
        return board -> board.getId().equals(boardId);
    }

    @Override
    public void save() {

    }

    @Override
    public List<Board> findAll() {
        return boards;
    }
}
