package phd.sa.csie.ntut.edu.tw.usecase.card.dto;

import phd.sa.csie.ntut.edu.tw.model.aggregate.card.Card;

import java.util.UUID;

public class CardDTOConverter {
    public static CardDTO toDTO(Card card) {
        CardDTO cardDTO = new CardDTO();

        cardDTO.setID(card.getID().toString());
        cardDTO.setName(card.getName());

        return cardDTO;
    }

    public static Card toEntity(CardDTO cardDTO) {
        return new Card(UUID.fromString(cardDTO.getID()),
                        cardDTO.getName());
    }
}