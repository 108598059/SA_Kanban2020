package domain.usecase.card.create;

public interface CreateCardInput {
    public void setCardName( String name ) ;
    public String getCardName() ;

    public String getWorkflowId();
    public void setWorkflowId(String workflowId);

    public String getStageId();
    public void setStageId(String stageId);

    public String getSwimlaneId();
    public void setSwimlaneId(String swimlaneId);
}
