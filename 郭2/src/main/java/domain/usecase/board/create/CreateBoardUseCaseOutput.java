package domain.usecase.board.create;

public interface CreateBoardUseCaseOutput {
    void setBoardId(String boardId);

    String getBoardId();

    void setBoardName(String boardName);

    String getBoardName();
}
