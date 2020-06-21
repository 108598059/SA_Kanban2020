package ddd.kanban.domain.model.kanbanboard.workflow;

import ddd.kanban.domain.model.Entity;

import java.util.ArrayList;
import java.util.List;

public abstract class Lane extends Entity {

    private String workflowId;
    private List<CommittedCard> committedCards;

    public Lane(final String id, String title, String workflowId){
        super(id, title);
        this.workflowId = workflowId;
        committedCards = new ArrayList<>();
    }

    public String commitCard(String cardId) {
        this.committedCards.add(new CommittedCard(cardId, workflowId));
        return cardId;
    }

    public List<CommittedCard> getCommittedCards() {
        return committedCards;
    }

    public void setCommittedCards(List<CommittedCard> committedCards){
        this.committedCards = committedCards;
    }

    public boolean unCommitCard(String cardId) {
        for(CommittedCard each : committedCards){
            if (each.getCardId().equalsIgnoreCase(cardId))
                return committedCards.remove(each);
        }
        return false;
    }

    public String getWorkflowId() {
        return workflowId;
    }
}
