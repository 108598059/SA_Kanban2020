package ddd.kanban.usecase.repository;

import ddd.kanban.usecase.card.entity.CardEntity;

public interface CardRepository {
    void add(CardEntity card);

    void update(CardEntity card);

    void save(CardEntity card);

    CardEntity findById(String cardId);
}
