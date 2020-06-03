package ddd.kanban.usecase.board.mapper;

import ddd.kanban.domain.model.board.Board;
import ddd.kanban.usecase.board.entity.BoardEntity;

public class BoardEntityMapper {
    public static Board mappingBoardFrom(BoardEntity boardEntity){
        return new Board(boardEntity.getId(), boardEntity.getTitle(), boardEntity.getDescription()
                , boardEntity.getWorkflowIds());
    }

    public static BoardEntity mappingBoardEntityFrom(Board board){
        return new BoardEntity(board.getId(), board.getTitle(), board.getDescription(), board.getWorkflowIds());
    }
}
