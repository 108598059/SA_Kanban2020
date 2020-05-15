package phd.sa.csie.ntut.edu.tw.domain.model.card.event;

import phd.sa.csie.ntut.edu.tw.domain.model.AbstractDomainEvent;
import phd.sa.csie.ntut.edu.tw.domain.model.Entity;
import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;

public class CardCreated extends AbstractDomainEvent {
    public CardCreated(String sourceId, String sourceName) {
        super(sourceId, sourceName);
    }

    public CardCreated(Entity entity) {
        super(entity);
    }

    @Override
    public Card getEntity() {
        return (Card) super.getEntity();
    }
}
