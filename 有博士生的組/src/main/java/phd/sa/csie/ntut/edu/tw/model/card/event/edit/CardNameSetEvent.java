package phd.sa.csie.ntut.edu.tw.model.card.event.edit;

import phd.sa.csie.ntut.edu.tw.model.domain.AbstractDomainEvent;

public class CardNameSetEvent extends AbstractDomainEvent {

    public CardNameSetEvent(String cardID, String cardName) {
        super(cardID, "[Card Name Set Event] Card: " + cardID + " set \"" + cardName + "\" as card name.");
    }
}
