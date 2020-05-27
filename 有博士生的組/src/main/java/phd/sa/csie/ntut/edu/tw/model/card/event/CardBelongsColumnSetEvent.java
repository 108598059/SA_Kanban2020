package phd.sa.csie.ntut.edu.tw.model.card.event;

import phd.sa.csie.ntut.edu.tw.model.AbstractDomainEvent;

public class CardBelongsColumnSetEvent extends AbstractDomainEvent {
    public CardBelongsColumnSetEvent(String cardID, String belongsColumnID) {
        super(cardID, "[Card Belongs Column Set Event] card: " + cardID + " belongs to column: " + belongsColumnID);
    }
}
