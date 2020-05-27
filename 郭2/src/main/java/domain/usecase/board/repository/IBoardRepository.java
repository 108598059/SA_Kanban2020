package domain.usecase.board.repository;

import domain.usecase.board.BoardEntity;

import java.util.List;

public interface IBoardRepository {
    void add(BoardEntity board);

    BoardEntity getBoardById(String boardId);

    void save(BoardEntity board);

    List<BoardEntity> getAllBoard();
}
