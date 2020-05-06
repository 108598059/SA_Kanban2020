package domain.usecase.workflow.commit;

public interface CommitCardInput {
    void setWorkflowId(String workflowId);

    void setSwimlaneId(String swimlaneId);

    void setStageId(String stageId);

    void setCardId(String cardId);

    String getWorkflowId();

    String getStageId();

    String getSwimlaneId();

    String getCardId();
}
