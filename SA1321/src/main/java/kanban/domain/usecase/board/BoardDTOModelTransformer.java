package kanban.domain.usecase.board;

import kanban.domain.model.aggregate.board.Board;

public class BoardDTOModelTransformer {
    public static BoardDTO transformModelToDTO(Board board){
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardId(board.getBoardId());
        boardDTO.setBoardName(board.getBoardName());
        boardDTO.setUserId(board.getUserId());
        boardDTO.setWorkflowIds(board.getWorkflowIds());
        return boardDTO;
    }
}
