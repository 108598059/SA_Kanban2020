package tw.teddysoft.clean.domain.model.card.event;

import tw.teddysoft.clean.domain.model.AbstractDomainEvent;
import tw.teddysoft.clean.domain.model.Entity;
import tw.teddysoft.clean.domain.model.card.Card;

public class CardDeleted extends AbstractDomainEvent {

    private final String workflowId;
    private final String laneId;

    public CardDeleted(String cardId, String cardName, String workflowId, String laneId) {
        super(cardId, cardName);
        this.workflowId = workflowId;
        this.laneId = laneId;
    }

    public String getCardId(){
        return this.getSourceId();
    }

    public String getCardName(){
        return this.getSourceName();
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getLaneId() {
        return laneId;
    }

    @Override
    public Card getEntity(){
        return (Card) super.getEntity();
    }

    @Override
    public String detail() {
        String formatDate = String.format("occurredOn='%1$tY-%1$tm-%1$td %1$tH:%1$tM']", occurredOn());
        String format = String.format(
                "%s[Name='%s', id='%s'] ",
                this.getClass().getSimpleName(),
                this.getCardName(), this.getCardId());
        return format + formatDate;
    }

}
