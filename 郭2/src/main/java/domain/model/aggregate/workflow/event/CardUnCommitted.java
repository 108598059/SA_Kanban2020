package domain.model.aggregate.workflow.event;

import domain.model.FlowEvent;

public class CardUnCommitted extends FlowEvent {
    public CardUnCommitted(String workflowId, String laneId, String cardId) {
        super(workflowId, laneId, cardId);
    }
}
