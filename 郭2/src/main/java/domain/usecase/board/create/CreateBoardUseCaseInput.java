package domain.usecase.board.create;

public class CreateBoardUseCaseInput {
    private String boardName;
    private String userId;

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
