package domain.adapter.view_model;

import domain.adapter.BoardEntityDto;

public class CreateBoardViewModel implements ViewModel<BoardEntityDto> {
    private String boardId;
    private String boardName;

    @Override
    public ViewModel<BoardEntityDto> setViewModel(BoardEntityDto boardEntityDto) {
        boardId = boardEntityDto.getBoardId();
        boardName = boardEntityDto.getBoardName();
        return this;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getBoardName() {
        return boardName;
    }
}
