package phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.move.dto.left;

import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.domain.dto.DomainEventDTO;

public class CardLeftColumnEventDTO extends DomainEventDTO {
    private String cardID;
    private String columnID;

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getColumnID() {
        return columnID;
    }

    public void setColumnID(String columnID) {
        this.columnID = columnID;
    }
}
