package domain.model.aggregate.workflow.event;

import domain.model.FlowEvent;

public class CardCommitted extends FlowEvent {
    public CardCommitted(String workflowId, String laneId, String cardId) {
        super(workflowId, laneId, cardId);
    }
}
