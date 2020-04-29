package domain.adapter.repository.card;

import domain.model.aggregate.card.Card;
import domain.usecase.card.repository.ICardRepository;

import java.util.ArrayList;

public class InMemoryCardRepository implements ICardRepository {
    private ArrayList<Card> cardList = new ArrayList<Card>();

    public void add(Card card) {
        cardList.add(card);
    }

    public Card getCardById(String cardId){
        for (Card each:cardList) {
            if(cardId.equals(each.getCardId()))
                return each;
        }
        throw new RuntimeException("not found cardId = " + cardId);
    }

    public void save(Card card) {
        for (Card each : cardList) {
            if (each.getCardId().equals(card.getCardId())) {
                cardList.set(cardList.indexOf(each), card);
                break;
            }
        }
    }
}
