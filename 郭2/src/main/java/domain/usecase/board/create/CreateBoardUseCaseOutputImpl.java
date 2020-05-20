package domain.usecase.board.create;

public class CreateBoardUseCaseOutputImpl implements CreateBoardUseCaseOutput {
    private String boardId;
    private String boardName;

    @Override
    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    @Override
    public String getBoardId() {
        return boardId;
    }

    @Override
    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    @Override
    public String getBoardName() {
        return boardName;
    }
}

