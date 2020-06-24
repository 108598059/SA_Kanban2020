package ddd.kanban.domain.model.card.event;

import ddd.kanban.domain.model.AbstractDomainEvent;
import ddd.kanban.domain.model.DomainEvent;
import ddd.kanban.domain.model.common.DateProvider;

import java.util.Date;

public class CardCreated extends AbstractDomainEvent {
    private String workflowId;
    private String laneId;

    public CardCreated(String cardId, String workflowId, String laneId, String cardTitle, String id){
        super(cardId, cardTitle, id);
        this.workflowId = workflowId;
        this.laneId = laneId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getLaneId() {
        return laneId;
    }
}
