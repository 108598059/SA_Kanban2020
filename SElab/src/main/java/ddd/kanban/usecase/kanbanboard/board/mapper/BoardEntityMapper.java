package ddd.kanban.usecase.kanbanboard.board.mapper;

import ddd.kanban.domain.model.kanbanboard.board.Board;
import ddd.kanban.usecase.kanbanboard.board.entity.BoardEntity;

public class BoardEntityMapper {
    public static Board mappingBoardFrom(BoardEntity boardEntity){
        return new Board(boardEntity.getId(), boardEntity.getTitle(), boardEntity.getDescription()
                , boardEntity.getWorkflowIds());
    }

    public static BoardEntity mappingBoardEntityFrom(Board board){
        BoardEntity boardEntity = new BoardEntity(board.getId(), board.getTitle(), board.getDescription());
        boardEntity.setWorkflowIds(board.getWorkflowIds());
        return boardEntity;
    }
}
