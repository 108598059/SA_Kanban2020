package phd.sa.csie.ntut.edu.tw.model.aggregate.card.event.edit;

import phd.sa.csie.ntut.edu.tw.model.domain.AbstractDomainEvent;

public class CardNameEditedEvent extends AbstractDomainEvent {

    public CardNameEditedEvent(String cardID, String cardName) {
        super(cardID, "[Card Name Edited Event] Card: " + cardID + " edit name as \"" + cardName + "\".");
    }
}
