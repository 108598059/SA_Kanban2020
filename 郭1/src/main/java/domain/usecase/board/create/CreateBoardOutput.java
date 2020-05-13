package domain.usecase.board.create;

public interface CreateBoardOutput {
    public String getBoardId();
    public void setBoardId(String boardId);
    public void setBoardName(String boardName);
    public String getBoardName();
}
