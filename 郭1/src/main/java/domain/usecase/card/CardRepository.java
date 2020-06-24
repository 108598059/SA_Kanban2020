package domain.usecase.card;

import domain.entity.aggregate.card.Card;

public interface CardRepository {
    void save(CardDTO cardDTO);
    void add(CardDTO cardDTO);
    CardDTO getCardById(String id);
}
