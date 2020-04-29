package ddd.kanban.usecase.workflow.create;

public class CreateWorkflowInput {

    private String workflowTitle;
    private  String workflowBoardId;

    public CreateWorkflowInput(String workflowTitle, String workflowBoardId){
        this.workflowTitle = workflowTitle;
        this.workflowBoardId = workflowBoardId;
    }

    public String getWorkflowTitle() {return workflowTitle;}

    public  String getWorkflowBoardId(){return  workflowBoardId; }
}
