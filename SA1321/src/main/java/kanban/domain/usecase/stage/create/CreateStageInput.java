package kanban.domain.usecase.stage.create;

public interface CreateStageInput {

    public String getStageName();

    public void setStageName(String stageName);

    public String getWorkflowId();

    public void setWorkflowId(String workflowId);
}
