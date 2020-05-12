package phd.sa.csie.ntut.edu.tw.usecase.card.dto;

import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.dto.DTO;
import phd.sa.csie.ntut.edu.tw.usecase.dto.DTOConverter;

public class CardDTOConverter implements DTOConverter<Card> {

    @Override
    public CardDTO toDTO(Card entity) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setId(entity.getId());
        cardDTO.setName(entity.getName());
        cardDTO.setColumnId(entity.getColumnId());
        return cardDTO;
    }

    @Override
    public Card toEntity(DTO dto) {
        CardDTO cardDTO = (CardDTO) dto;
        // Reconstruct card objects by DTO purely.
        Card card = new Card(cardDTO.getId(), cardDTO.getName(), cardDTO.getColumnId());
        // Reconstruct card objects by DTO and events.
        /* Card card = new Card(cardDTO.getId(), cardDTO.getName()); */
        return card;
    }
    
}