package ddd.kanban.usecase.board.create;

public class CreateBoardInput {

    private String boardId;
    private String boardName;
    private String boardDescription;

    public CreateBoardInput(String boardId, String boardName, String boardDescription){
        this.boardId = boardId;
        this.boardName = boardName;
        this.boardDescription = boardDescription;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getBoardName() {
        return boardName;
    }

    public String getBoardDescription() {
        return boardDescription;
    }
}
