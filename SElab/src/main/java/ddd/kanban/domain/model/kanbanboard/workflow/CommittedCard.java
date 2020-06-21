package ddd.kanban.domain.model.kanbanboard.workflow;

public class CommittedCard {
    private String cardId;
    private String workflowId;

    public CommittedCard(String cardId, String workflowId){
        this.cardId = cardId;
        this.workflowId = workflowId;
    }

    public String getCardId() {
        return cardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }
}
