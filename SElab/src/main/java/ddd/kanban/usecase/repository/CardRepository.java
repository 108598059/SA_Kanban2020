package ddd.kanban.usecase.repository;

import ddd.kanban.domain.model.card.Card;

public interface CardRepository {
    void add(Card card);

    void update(Card card);

    void save(Card card);

    Card findById(String cardId);
}
