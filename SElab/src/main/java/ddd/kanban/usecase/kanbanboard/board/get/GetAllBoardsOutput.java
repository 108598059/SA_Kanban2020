package ddd.kanban.usecase.kanbanboard.board.get;

import ddd.kanban.usecase.kanbanboard.board.dto.BoardDTO;

import java.util.List;

public interface GetAllBoardsOutput {

    List<BoardDTO> getBoards();

    void setBoards(List<BoardDTO> boards);
}
