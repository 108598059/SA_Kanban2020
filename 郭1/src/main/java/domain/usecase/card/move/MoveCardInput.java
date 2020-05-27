package domain.usecase.card.move;

public interface MoveCardInput {
    public void setFromSwimlaneId(String fromSwimlaneId);
    public void setToSwimlaneId(String toSwimlaneId);
    public void setWorkflowId(String workflowId);
    public void setFromStageId(String fromStageId);
    public void setToStageId(String toStageId);
    public void setCardId(String cardId);

    public String getFromSwimlaneId();
    public String getToSwimlaneId();
    public String getWorkflowId();
    public String getFromStageId();
    public String getToStageId();
    public String getCardId();
}
