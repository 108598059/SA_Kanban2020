package domain.model.card;

import domain.model.AggregateRoot;
import domain.model.card.event.CardCreated;

public class Card extends AggregateRoot {

    private String workflowId;

    private String laneId;


    public Card(String cardName, String laneId, String workflowId) {
        super(cardName);
        this.laneId = laneId;
        this.workflowId = workflowId;
        addDomainEvent(new CardCreated(cardName, id, laneId, workflowId));
    }

    public Card(String cardName, String cardId) {
        super(cardName, cardId);
        addDomainEvent(new CardCreated(cardName, cardId, laneId, workflowId));
    }

    public String getWorkflowId(){
        return workflowId;
    }

    public String getLaneId() {
        return laneId;
    }

}