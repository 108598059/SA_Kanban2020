package phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.move.dto.left;

import phd.sa.csie.ntut.edu.tw.model.board.event.move.CardLeftColumnEvent;

public class CardLeftColumnEventDTOConverter {
    public static CardLeftColumnEventDTO toDTO(CardLeftColumnEvent e) {
        CardLeftColumnEventDTO cardLeftColumnEventDTO = new CardLeftColumnEventDTO();

        cardLeftColumnEventDTO.setEventVersion(e.eventVersion());
        cardLeftColumnEventDTO.setOccurredTime(e.occurredOn());
        cardLeftColumnEventDTO.setDetail(e.detail());
        cardLeftColumnEventDTO.setSourceID(e.getSourceID());
        cardLeftColumnEventDTO.setSourceName(e.getSourceName());
        cardLeftColumnEventDTO.setCardID(e.getCardID());
        cardLeftColumnEventDTO.setColumnID(e.getColumnID());

        return cardLeftColumnEventDTO;
    }

    public static CardLeftColumnEvent toEntity(CardLeftColumnEventDTO e) {
        return new CardLeftColumnEvent(e.getOccurredTime(), e.getSourceID(), e.getSourceName(), e.getID(), e.getCardID(), e.getColumnID());
    }
}
