package ddd.kanban.usecase.board.get;

import ddd.kanban.usecase.board.DTO.BoardDTO;

import java.util.List;

public interface GetAllBoardsOutput {

    List<BoardDTO> getBoards();

    void setBoards(List<BoardDTO> boards);
}
