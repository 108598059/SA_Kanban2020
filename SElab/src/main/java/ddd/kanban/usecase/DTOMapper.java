package ddd.kanban.usecase;

import ddd.kanban.domain.model.board.Board;
import ddd.kanban.usecase.DTO.BoardDTO;

public class DTOMapper {

    public BoardDTO mappingBoardDTOFrom(Board board){
        BoardDTO boardDTO = new BoardDTO(board.getId(), board.getTitle(), board.getDescription());
        boardDTO.setWorkflowIds(board.getWorkflowIds());
        return boardDTO;
    }
}
