package phd.sa.csie.ntut.edu.tw.usecase.card.dto;

import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.dto.DTO;

import java.util.UUID;

public class CardDTOConverter {
    public static CardDTO toDTO(Card entity) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setId(entity.getId().toString());
        cardDTO.setName(entity.getName());
        cardDTO.setColumnId(entity.getColumnId().toString());
        return cardDTO;
    }

    public static Card toEntity(DTO dto) {
        CardDTO cardDTO = (CardDTO) dto;
        UUID cardId = UUID.fromString(cardDTO.getId());
        String cardName = cardDTO.getName();
        UUID boardId = UUID.fromString(cardDTO.getColumnId());
        UUID columnId = UUID.fromString(cardDTO.getColumnId());
        Card card = new Card(cardId, cardName, boardId, columnId);
        return card;
    }
}