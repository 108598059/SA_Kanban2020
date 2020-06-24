package ddd.kanban.usecase.kanbanboard.board.create;

public interface CreateBoardOutput {

    void setBoardId(String boardId);

    void setBoardTitle(String boardTitle);

    void setBoardDescription(String boardDescription);

    String getBoardId();

    String getBoardTitle();

    String getBoardDescription();

}
