package domain.usecase.board;

import domain.entity.aggregate.board.Board;

public class BoardTransformer {

    public static BoardDTO toDTO(Board board){
        BoardDTO boardDTO = new BoardDTO();

        boardDTO.setId(board.getId());
        boardDTO.setName(board.getName());
        boardDTO.setWorkflows(board.getWorkflows());

        return boardDTO;
    }

    public static Board toBoard(BoardDTO boardDTO){
        Board board = new Board(boardDTO.getId(),
                boardDTO.getName(),
                boardDTO.getWorkflows());

        return board;
    }
}
