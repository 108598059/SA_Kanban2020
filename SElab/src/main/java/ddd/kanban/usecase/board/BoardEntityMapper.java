package ddd.kanban.usecase.board;

import ddd.kanban.domain.model.board.Board;
import ddd.kanban.usecase.board.Entity.BoardEntity;

public class BoardEntityMapper {
    public static Board mappingBoardFrom(BoardEntity boardEntity){
        Board board = new Board(boardEntity.getId(), boardEntity.getTitle(), boardEntity.getDescription());
        board.setWorkflowIds(boardEntity.getWorkflowIds());
        return  board;
    }

    public static BoardEntity mappingBoardEntityFrom(Board board){
        BoardEntity boardDTO = new BoardEntity(board.getId(), board.getTitle(), board.getDescription());
        boardDTO.setWorkflowIds(board.getWorkflowIds());
        return boardDTO;
    }
}
