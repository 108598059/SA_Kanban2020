package kanban.domain.usecase.card.repository;

import kanban.domain.model.aggregate.card.Card;
import kanban.domain.usecase.card.CardEntity;

public interface ICardRepository {
    public void add(CardEntity cardEntity);

    public CardEntity getCardById(String cardId);

    public void save(CardEntity cardEntity);
}
