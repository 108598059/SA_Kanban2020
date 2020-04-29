package ddd.kanban.domain.model.workflow;

import java.util.ArrayList;
import java.util.List;

public abstract class Lane {

    private String title;
    private final String id;
    private String workflowId;
    private final List<CommittedCard> committedCards;

    public Lane(String id, String title, String workflowId){
        this.id = id;
        this.title = title;
        this.workflowId = workflowId;
        committedCards = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String commitCard(String cardId, String workflowId) {
        this.committedCards.add(new CommittedCard(cardId, workflowId));
        return cardId;
    }

    public List<CommittedCard> getCommittedCards() {
        return committedCards;
    }
}
