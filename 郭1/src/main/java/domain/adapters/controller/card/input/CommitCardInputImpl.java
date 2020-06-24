package domain.adapters.controller.card.input;

import domain.usecase.workflow.commit.CommitCardInput;

public class CommitCardInputImpl implements CommitCardInput {

    private String workflowId;
    private String swimlaneId;
    private String stageId;
    private String cardId;

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public void setSwimlaneId(String swimlaneId) {
        this.swimlaneId = swimlaneId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getSwimlaneId() {
        return swimlaneId;
    }

    public String getCardId() {
        return cardId;
    }

    public String getStageId() {
        return stageId;
    }
}
