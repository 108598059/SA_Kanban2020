package phd.sa.csie.ntut.edu.tw.model.card.event;

import phd.sa.csie.ntut.edu.tw.model.AbstractDomainEvent;

public class CardNameSetEvent extends AbstractDomainEvent {

    public CardNameSetEvent(String cardID, String cardName) {
        super(cardID, "[Card Name Set Event] card: " + cardID + " set \"" + cardName + "\" as card name.");
    }
}
