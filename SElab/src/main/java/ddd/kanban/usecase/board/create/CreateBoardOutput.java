package ddd.kanban.usecase.board.create;

public class CreateBoardOutput {
    private String boardId;
    private String boardTitle;
    private String boardDescription;

    public CreateBoardOutput(){

    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardName) {
        this.boardTitle = boardName;
    }

    public String getBoardDescription() {
        return boardDescription;
    }

    public void setBoardDescription(String boardDescription) {
        this.boardDescription = boardDescription;
    }
}
