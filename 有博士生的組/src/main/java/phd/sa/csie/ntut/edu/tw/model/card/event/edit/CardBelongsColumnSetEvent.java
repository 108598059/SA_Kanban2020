package phd.sa.csie.ntut.edu.tw.model.card.event.edit;

import phd.sa.csie.ntut.edu.tw.model.domain.AbstractDomainEvent;

public class CardBelongsColumnSetEvent extends AbstractDomainEvent {
    public CardBelongsColumnSetEvent(String cardID, String belongsColumnID) {
        super(cardID, "[Card Belongs Column Set Event] Card: " + cardID + " belongs to column: " + belongsColumnID);
    }
}
