package phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.move.dto.entered;

import phd.sa.csie.ntut.edu.tw.model.board.event.move.CardEnteredColumnEvent;

public class CardEnteredColumnEventDTOConverter {
    public static CardEnteredColumnEventDTO toDTO(CardEnteredColumnEvent e) {
        CardEnteredColumnEventDTO cardEnteredColumnEventDTO = new CardEnteredColumnEventDTO();

        cardEnteredColumnEventDTO.setEventVersion(e.eventVersion());
        cardEnteredColumnEventDTO.setOccurredTime(e.occurredOn());
        cardEnteredColumnEventDTO.setDetail(e.detail());
        cardEnteredColumnEventDTO.setSourceID(e.getSourceID());
        cardEnteredColumnEventDTO.setSourceName(e.getSourceName());
        cardEnteredColumnEventDTO.setCardID(e.getCardID());
        cardEnteredColumnEventDTO.setColumnID(e.getColumnID());

        return cardEnteredColumnEventDTO;
    }
}
