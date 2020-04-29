package ddd.kanban.usecase.workflow.create;

public class CreateWorkflowInput {

    private String workflowTitle;
    private String workflowId;
    private  String workflowBoardId;
    public CreateWorkflowInput(String workflowId,String workflowTitle,String workflowBoardId){
        this.workflowTitle = workflowTitle;
        this.workflowId = workflowId;
        this.workflowBoardId = workflowBoardId;
    }

    public String getWorkflowTitle() {return workflowTitle;}

    public String getWorkflowId() { return workflowId; }

    public  String getWorkflowBoardId(){return  workflowBoardId; }
}
