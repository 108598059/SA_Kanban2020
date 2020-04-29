package domain.usecase.board.repository;

import domain.model.aggregate.board.Board;

public interface IBoardRepository {
    void add(Board board);

    Board getBoardById(String boardId);

    void save(Board board);
}
