package domain.entity.aggregate.workflow.event;

import domain.entity.FlowEvent;

public class CardMoved extends FlowEvent {

    public CardMoved(String fromStageId, String toStageId, String fromSwimlaneId, String toSwimlaneId, String cardId){
        super(fromStageId, toStageId, fromSwimlaneId, toSwimlaneId, cardId);

    }


}
