package domain.usecase.card;

import domain.model.aggregate.card.Card;

public class CardTransfer {
    public static CardDTO CardToCardDTO(Card card) {
        CardDTO cardDTO = new CardDTO();

        cardDTO.setCardContent(card.getCardContent());
        cardDTO.setCardId(card.getCardId());
        cardDTO.setCardName(card.getCardName());
        cardDTO.setCardType(card.getCardType());
        cardDTO.setLaneId(card.getLaneId());
        cardDTO.setTaskList(card.getTaskList());
        cardDTO.setWorkflowId(card.getWorkflowId());

        return cardDTO;
    }

    public static Card CardDTOToCard(CardDTO cardDTO) {
        Card card = new Card(cardDTO.getCardName());

        card.setCardId(cardDTO.getCardId());
        card.setCardType(cardDTO.getCardType());
        card.setCardContent(cardDTO.getCardContent());
        card.setTaskList(cardDTO.getTaskList());
        card.setCardName(cardDTO.getCardName());
        card.setLaneId(cardDTO.getLaneId());
        card.setWorkflowId(cardDTO.getWorkflowId());

        return card;
    }
}
