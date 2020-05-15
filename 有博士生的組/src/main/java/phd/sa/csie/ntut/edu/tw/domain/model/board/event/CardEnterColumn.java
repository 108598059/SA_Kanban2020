package phd.sa.csie.ntut.edu.tw.domain.model.board.event;

import phd.sa.csie.ntut.edu.tw.domain.model.AbstractDomainEvent;

public class CardEnterColumn extends AbstractDomainEvent {

    private String columnId;
    private String cardId;

    public CardEnterColumn(String sourceId, String columnId, String cardId) {
        super(sourceId, "Entered column event: " + columnId);
        this.columnId = columnId;
        this.cardId = cardId;
    }

    public String getColumnId() {
        return this.columnId;
    }

    public String getCardId() {
        return cardId;
    }
}
