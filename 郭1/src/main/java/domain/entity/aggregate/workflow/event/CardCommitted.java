package domain.entity.aggregate.workflow.event;

import domain.entity.FlowEvent;

public class CardCommitted extends FlowEvent {
    public CardCommitted(String fromStageId, String toStageId, String fromSwimlaneId, String toSwimlaneId, String cardId) {
        super(fromStageId, toStageId, fromSwimlaneId, toSwimlaneId, cardId);
    }
}
