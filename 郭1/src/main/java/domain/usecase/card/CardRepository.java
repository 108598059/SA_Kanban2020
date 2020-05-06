package domain.usecase.card;

import domain.entity.card.Card;

public interface CardRepository {
    void save(Card card);
    void add(Card card);
    Card getCardById(String id);
}
