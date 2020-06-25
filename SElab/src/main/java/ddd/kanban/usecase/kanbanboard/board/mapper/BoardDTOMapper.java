package ddd.kanban.usecase.kanbanboard.board.mapper;

import ddd.kanban.domain.model.kanbanboard.board.Board;
import ddd.kanban.usecase.kanbanboard.board.dto.BoardDTO;

public class BoardDTOMapper {

    public static BoardDTO mappingBoardDTOFrom(Board board){
        return new BoardDTO(board.getId(), board.getTitle(), board.getDescription(), board.getWorkflowIds());
    }
}
