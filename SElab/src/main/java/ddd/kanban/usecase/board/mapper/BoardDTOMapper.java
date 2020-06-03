package ddd.kanban.usecase.board.mapper;

import ddd.kanban.domain.model.board.Board;
import ddd.kanban.usecase.board.dto.BoardDTO;

public class BoardDTOMapper {

    public static BoardDTO mappingBoardDTOFrom(Board board){
        return new BoardDTO(board.getId(), board.getTitle(), board.getDescription(), board.getWorkflowIds());
    }
}
