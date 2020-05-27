package ddd.kanban.domain.model.card.event;

import ddd.kanban.domain.model.FlowEvent;

public class CardUnCommitted extends FlowEvent {
    public CardUnCommitted(String cardId, String workflowId, String laneId, String summary, String id) {
        super(cardId, workflowId, laneId, summary, id);
    }
}
