package ddd.kanban.usecase.kanbanboard.board.create;

public class CreateBoardInput {

    private String boardTitle;
    private String boardDescription;

    public CreateBoardInput(String boardTitle, String boardDescription){
        this.boardTitle = boardTitle;
        this.boardDescription = boardDescription;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public String getBoardDescription() {
        return boardDescription;
    }
}
