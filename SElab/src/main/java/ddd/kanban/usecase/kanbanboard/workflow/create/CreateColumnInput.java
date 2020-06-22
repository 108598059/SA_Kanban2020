package ddd.kanban.usecase.kanbanboard.workflow.create;

public class CreateColumnInput {
    private String workflowId;
    private String columnTitle;

    public CreateColumnInput(String columnTitle, String workflowId){
        this.columnTitle = columnTitle;
        this.workflowId = workflowId;
    }

    public String getColumnTitle() {
        return columnTitle;
    }

    public String getWorkflowId() {
        return workflowId;
    }


}
