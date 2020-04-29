package ddd.kanban.usecase.board.create;

public class CreateBoardInput {

    private String boardName;
    private String boardDescription;

    public CreateBoardInput(String boardName, String boardDescription){
        this.boardName = boardName;
        this.boardDescription = boardDescription;
    }

    public String getBoardName() {
        return boardName;
    }

    public String getBoardDescription() {
        return boardDescription;
    }
}
