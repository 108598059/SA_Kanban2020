package phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.move.dto.entered;

import phd.sa.csie.ntut.edu.tw.model.aggregate.board.event.move.CardEnteredColumnEvent;

public class CardEnteredColumnEventDTOConverter {
    public static CardEnteredColumnEventDTO toDTO(CardEnteredColumnEvent e) {
        CardEnteredColumnEventDTO cardEnteredColumnEventDTO = new CardEnteredColumnEventDTO();

        cardEnteredColumnEventDTO.setEventVersion(e.eventVersion());
        cardEnteredColumnEventDTO.setOccurredTime(e.occurredOn());
        cardEnteredColumnEventDTO.setDetail(e.detail());
        cardEnteredColumnEventDTO.setSourceID(e.getSourceID());
        cardEnteredColumnEventDTO.setSourceName(e.getSourceName());
        cardEnteredColumnEventDTO.setCardID(e.getCardID());
        cardEnteredColumnEventDTO.setColumnID(e.getToColumnID());

        return cardEnteredColumnEventDTO;
    }

    public static CardEnteredColumnEvent toEntity(CardEnteredColumnEventDTO e) {
        return new CardEnteredColumnEvent(e.getOccurredTime(), e.getSourceID(), e.getSourceName(), e.getID(), e.getCardID(), e.getColumnID());
    }
}
