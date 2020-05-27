package phd.sa.csie.ntut.edu.tw.model.card.event.calculate;

import phd.sa.csie.ntut.edu.tw.model.domain.AbstractDomainEvent;

public class LeadTimeCalculatedEvent extends AbstractDomainEvent {
    public LeadTimeCalculatedEvent(String cardID, long leadTime) {
        super(cardID, "[Lead Time Calculated Event] The lead time of card: " + cardID + " is " + leadTime);
    }
}
