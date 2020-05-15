package phd.sa.csie.ntut.edu.tw.usecase.card.dto;

import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.DTO;

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
        Card card = new Card(UUID.fromString(cardDTO.getId()), cardDTO.getName(), UUID.fromString(cardDTO.getColumnId()));
        return card;
    }
}