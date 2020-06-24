package domain.usecase.board.repository;

import domain.usecase.board.BoardDTO;

import java.util.List;

public interface IBoardRepository {
    void add(BoardDTO board);

    BoardDTO getBoardById(String boardId);

    void save(BoardDTO board);

    List<BoardDTO> getAllBoard();
}
