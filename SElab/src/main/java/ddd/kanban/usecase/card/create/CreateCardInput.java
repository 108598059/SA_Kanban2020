package ddd.kanban.usecase.card.create;

public class CreateCardInput {

    private String cardTitle;
    private String boardId;
    private String workflowId;
    private String columnId;

    public CreateCardInput(String cardTitle, String boardId, String workflowId, String ColumnId){
        this.cardTitle = cardTitle;
        this.boardId = boardId;
        this.workflowId = workflowId;
        this.columnId = ColumnId;
    }

    public String getCardTitle() {
        return cardTitle;
    }


    public String getBoardId() {
        return boardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getColumnId(){
        return columnId;
    }

}
