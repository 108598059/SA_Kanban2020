package ddd.kanban.usecase.kanbanboard.workflow.create;

public class CreateWorkflowOutput {
    private String workflowTitle;
    private String workflowId;
    private String workflowBoardId;
    private String defaultColumnId;

    public String getWorkflowTitle() {
        return workflowTitle;
    }

    public void setWorkflowTitle(String workflowTitle) {
        this.workflowTitle = workflowTitle;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getWorkflowBoardId() {return workflowBoardId; }

    public void setWorkflowBoardId(String workflowBoardId) { this.workflowBoardId = workflowBoardId; }

    public String getDefaultColumnId() {
        return defaultColumnId;
    }

    public void setDefaultColumnId(String defaultColumnId) {
        this.defaultColumnId = defaultColumnId;
    }
}
