package phd.sa.csie.ntut.edu.tw.usecase.card.dto;

import phd.sa.csie.ntut.edu.tw.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.DTO;

import java.util.UUID;

public class CardDTOConverter {
    public static CardDTO toDTO(Card entity) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setID(entity.getID().toString());
        cardDTO.setName(entity.getName());
        cardDTO.setColumnID(entity.getColumnID().toString());
        return cardDTO;
    }

    public static Card toEntity(DTO dto) {
        CardDTO cardDTO = (CardDTO) dto;
        Card card = new Card(UUID.fromString(cardDTO.getID()), cardDTO.getName(), UUID.fromString(cardDTO.getColumnID()));
        return card;
    }
}