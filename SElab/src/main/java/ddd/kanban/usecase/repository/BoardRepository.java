package ddd.kanban.usecase.repository;



import ddd.kanban.domain.model.board.Board;

import java.util.List;

public interface BoardRepository {

    void add(Board board);

    Board findById(String boardId);

    void save();

    List<Board> findAll();
}
