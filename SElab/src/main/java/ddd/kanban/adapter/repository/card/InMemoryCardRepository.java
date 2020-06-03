package ddd.kanban.adapter.repository.card;

import ddd.kanban.usecase.card.entity.CardEntity;
import ddd.kanban.usecase.repository.CardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class InMemoryCardRepository implements CardRepository {
    private List<CardEntity> cards;

    public InMemoryCardRepository(){
        cards = new ArrayList<CardEntity>();
    }

    @Override
    public void add(CardEntity cardEntity){
        cards.add(cardEntity);
    }

    @Override
    public void update(CardEntity cardEntity){
        for(int i = 0 ; i < cards.size() ; i ++)
        {
            if(cards.get(i).getId().equals(cardEntity.getId()))
            {
                cards.set(i, cardEntity);
            }
        }
    }

    @Override
    public void save(CardEntity cardEntity){
        update(cardEntity); // TBD
    }

    @Override
    public CardEntity findById(String cardId){
        return cards.stream()
                .filter(findCardById(cardId))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private static Predicate<CardEntity> findCardById(String cardId){
        return cardEntity -> cardEntity.getId().equals(cardId);
    }
}
