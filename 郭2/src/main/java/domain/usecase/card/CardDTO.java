package domain.usecase.card;

import domain.model.aggregate.card.Task;

import java.util.List;

public class CardDTO {
    private String cardId;
    private String cardName;
    private String cardContent;
    private String cardType;
    private String workflowId;
    private String laneId;
    private List<Task> taskList;

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getLaneId() {
        return laneId;
    }

    public void setCardContent(String cardContent) {
        this.cardContent = cardContent;
    }

    public String getCardType() {
        return cardType;
    }

    public void setLaneId(String laneId) {
        this.laneId = laneId;
    }

    public String getCardContent() {
        return cardContent;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardName() {
        return cardName;
    }
}
