package ddd.kanban.usecase.workflow.create;

public class CreateWorkflowInput {

    private String workflowTitle;
    private  String boardId;

    public CreateWorkflowInput(String workflowTitle, String workflowBoardId){
        this.workflowTitle = workflowTitle;
        this.boardId = workflowBoardId;
    }

    public String getWorkflowTitle() {return workflowTitle;}

    public  String getBoardId(){return boardId; }
}
