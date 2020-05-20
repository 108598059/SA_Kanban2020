package ddd.kanban.usecase.repository;

import ddd.kanban.usecase.board.Entity.BoardEntity;

import java.util.List;

public interface BoardRepository {

    void add(BoardEntity boardEntity);

    BoardEntity findById(String boardId);

    void save(BoardEntity boardEntity);

    List<BoardEntity> findAll();
}
