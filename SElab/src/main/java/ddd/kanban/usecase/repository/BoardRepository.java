package ddd.kanban.usecase.repository;

import ddd.kanban.usecase.DTO.BoardDTO;

import java.util.List;

public interface BoardRepository {

    void add(BoardDTO boardDTO);

    BoardDTO findById(String boardId);

    void save(BoardDTO boardDTO);

    List<BoardDTO> findAll();
}
