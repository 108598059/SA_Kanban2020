package domain.usecase.board.repository;

import domain.usecase.entity.BoardEntity;

public interface IBoardRepository {
    void add(BoardEntity board);

    BoardEntity getBoardById(String boardId);

    void save(BoardEntity board);
}
