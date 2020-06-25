package tw.teddysoft.clean.domain.model.card.event;

import tw.teddysoft.clean.domain.model.AbstractDomainEvent;
import tw.teddysoft.clean.domain.model.Entity;
import tw.teddysoft.clean.domain.model.card.Card;

public class CardCreated extends AbstractDomainEvent {

    private final String laneId;

    public CardCreated(Entity entity, String laneId) {
        super(entity);
        this.laneId = laneId;
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
                this.getEntity().getName(), this.getEntity().getId());
        return format + formatDate;
    }

}
