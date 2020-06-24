package domain.entity.aggregate.card.event;

import domain.entity.DomainEvent;

public class SubtaskCreated implements DomainEvent {
    private String cardId ;
    private String subtaskId ;
    private String subtaskName ;

    public SubtaskCreated( String cardId, String subtaskId, String subtaskName ) {
        this.cardId = cardId ;
        this.subtaskId = subtaskId ;
        this.subtaskName = subtaskName ;
    }


    public String getCardId() {
        return cardId;
    }

    public String getSubtaskId() {
        return subtaskId;
    }

    public String getSubtaskName() {
        return subtaskName;
    }
}
