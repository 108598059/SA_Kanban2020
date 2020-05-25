package kanban.domain.model.aggregate.workflow.event;

import kanban.domain.model.DomainEvent;
import kanban.domain.model.common.DateProvider;

import java.util.Date;

public class CardUnCommitted implements DomainEvent {


    private String stageId;
    private String cardId;
    private Date occurredOn;

    public CardUnCommitted(String stageId, String cardId) {
        this.occurredOn = DateProvider.now();
        this.stageId = stageId;
        this.cardId = cardId;
    }

    @Override
    public Date getOccurredOn() {
        return occurredOn;
    }

    public String getStageId() {
        return stageId;
    }

    public String getCardId() {
        return cardId;
    }
}
