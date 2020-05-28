package domain.usecase.card.cycletime;

public interface CalculateCycleTimeInput {
    public String getWorkflowId();
    public String getCardId();
    public String getFromStageId();
    public String getToStageId();
    public String getFromSwimlaneId();
    public String getToSwimlaneId();


    public void setWorkflowId(String workflowId);
    public void setCardId(String cardId);
    public void setFromStageId(String fromStageId);
    public void setToStageId(String toStageId);
    public void setFromSwimlane(String fromSwimlaneId);
    public void setToSwimlane(String toSwimlaneId);
}
