package domain.usecase.card;


import domain.entity.aggregate.card.Card;

public class CardTransformer {
    public static CardDTO toDTO(Card card){
        CardDTO cardDTO = new CardDTO(card.getId(), card.getName(), card.getSubtasks());
        return cardDTO;
    }

    public static Card toCard(CardDTO cardDTO) {
        return new Card(cardDTO.getId(), cardDTO.getName(), cardDTO.getSubtasks());
    }
}
