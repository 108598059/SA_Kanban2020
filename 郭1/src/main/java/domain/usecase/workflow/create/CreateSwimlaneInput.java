package domain.usecase.workflow.create;

public interface CreateSwimlaneInput {
    public void setName(String name);

    public void setStageId(String Id);

    public void setWorkflowId(String id);

    public String getName();

    public String getStageId();

    public String getWorkflowId();
}
