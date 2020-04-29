package domain.usecase.workflow.create;

public interface CreateWorkflowInput {
    public void setWorkflowName(String name);
    public String getWorkflowName();

    public String getBoardId();
    public void setBoardId(String boardId);
}
