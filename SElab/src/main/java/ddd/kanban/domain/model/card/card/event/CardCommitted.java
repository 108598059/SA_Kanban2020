package ddd.kanban.domain.model.card.card.event;

import ddd.kanban.domain.model.FlowEvent;

public class CardCommitted extends FlowEvent {

    public CardCommitted(String cardId, String workflowId, String columnId, String id) {
        super(cardId, workflowId, columnId, id);
    }
}
