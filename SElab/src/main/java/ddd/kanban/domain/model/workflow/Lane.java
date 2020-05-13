package ddd.kanban.domain.model.workflow;

import ddd.kanban.domain.model.Entity;

import java.util.ArrayList;
import java.util.List;

public abstract class Lane extends Entity {

    private String workflowId;
    private final List<CommittedCard> committedCards;

    public Lane(final String id, String title, String workflowId){
        super(id, title);
        this.workflowId = workflowId;
        committedCards = new ArrayList<>();
    }

    public String commitCard(String cardId, String workflowId) {
        this.committedCards.add(new CommittedCard(cardId, workflowId));
        return cardId;
    }

    public List<CommittedCard> getCommittedCards() {
        return committedCards;
    }
}
