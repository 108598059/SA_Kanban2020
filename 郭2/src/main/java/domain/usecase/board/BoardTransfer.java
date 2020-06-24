package domain.usecase.board;

import domain.model.aggregate.board.Board;

public class BoardTransfer {
    public static BoardDTO BoardToBoardDTO(Board board) {
        BoardDTO boardDTO = new BoardDTO();

        boardDTO.setBoardId(board.getBoardId());
        boardDTO.setBoardName(board.getBoardName());
        boardDTO.setWorkflowIds(board.getWorkflowIds());

        return boardDTO;
    }

    public static Board BoardDTOToBoard(BoardDTO boardDTO) {
        Board board = new Board();

        board.setBoardId(boardDTO.getBoardId());
        board.setBoardName(boardDTO.getBoardName());
        board.setWorkflowIds(boardDTO.getWorkflowIds());

        return board;
    }
}
