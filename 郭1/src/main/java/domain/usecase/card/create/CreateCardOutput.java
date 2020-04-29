package domain.usecase.card.create;

public interface CreateCardOutput {
    public void setCardId(String id) ;
    public String getCardId() ;

    public String getWorkflowId();
    public void setWorkflowId(String workflowId);
}
