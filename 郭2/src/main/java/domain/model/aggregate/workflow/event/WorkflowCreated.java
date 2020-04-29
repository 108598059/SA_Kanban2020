package domain.model.aggregate.workflow.event;

public class WorkflowCreated {
    private String workflowId;
    private String boardId;

    public String getBoardId() {
        return boardId;
    }

    public WorkflowCreated(String boardId, String workflowId){
        this.workflowId = workflowId;
        this.boardId = boardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }
}
