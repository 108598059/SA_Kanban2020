package domain.usecase.workflow.commit;

public class CommitWorkflowUseCaseInput {
    private String boardId;
    private String workflowId;

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getBoardId() {
        return boardId;
    }
}
