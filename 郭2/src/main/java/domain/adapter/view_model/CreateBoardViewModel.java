package domain.adapter.view_model;


import domain.usecase.board.BoardDTO;

public class CreateBoardViewModel implements ViewModel<BoardDTO> {
    private String boardId;
    private String boardName;

    @Override
    public ViewModel<BoardDTO> setViewModel(BoardDTO boardDTO) {
        boardId = boardDTO.getBoardId();
        boardName = boardDTO.getBoardName();
        return this;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getBoardName() {
        return boardName;
    }
}
