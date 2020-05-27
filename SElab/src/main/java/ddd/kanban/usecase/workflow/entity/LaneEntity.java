package ddd.kanban.usecase.workflow.entity;

import ddd.kanban.domain.model.workflow.CommittedCard;

import java.util.List;

public class LaneEntity {
    private String id;
    private String title;
    private String workflowId;
    private List<CommittedCard> committedCards;

    public LaneEntity(String id, String title, String workflowId) {
        this.id = id;
        this.title = title;
        this.workflowId = workflowId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public List<CommittedCard> getCommittedCards() {
        return committedCards;
    }

    public void setCommittedCards(List<CommittedCard> committedCards) {
        this.committedCards = committedCards;
    }
}
