package domain.usecase.board;

import domain.entity.board.Board;

public interface BoardRepository {

    Board getBoardById(String id);
    void add(Board board);
    void save(Board board);
}
