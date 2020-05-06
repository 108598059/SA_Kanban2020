package domain.usecase;

import domain.model.aggregate.board.Board;
import domain.usecase.entity.BoardEntity;

public class BoardDTO {
    public static BoardEntity BoardToBoardEntity(Board board) {
        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setBoardId(board.getBoardId());
        boardEntity.setBoardName(board.getBoardName());
        boardEntity.setWorkflowIds(board.getWorkflowIds());

        return boardEntity;
    }

    public static Board BoardEntityToBoard(BoardEntity boardEntity) {
        Board board = new Board();

        board.setBoardId(boardEntity.getBoardId());
        board.setBoardName(boardEntity.getBoardName());
        board.setWorkflowIds(boardEntity.getWorkflowIds());

        return board;
    }
}
