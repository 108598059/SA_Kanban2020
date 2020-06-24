package domain.usecase.card.repository;

import domain.usecase.card.CardDTO;

public interface ICardRepository {
    void add(CardDTO card);

    CardDTO getCardById(String cardId);

    void save(CardDTO card);
}
