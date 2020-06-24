package domain.adapter.repository.card;

import domain.usecase.card.CardDTO;
import domain.usecase.card.repository.ICardRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCardRepository implements ICardRepository {
    private List<CardDTO> cardList = new ArrayList<>();

    public void add(CardDTO card) {
        cardList.add(card);
    }

    public CardDTO getCardById(String cardId){
        for (CardDTO each:cardList) {
            if(cardId.equals(each.getCardId()))
                return each;
        }
        throw new RuntimeException("not found cardId = " + cardId);
    }

    public void save(CardDTO card) {
        for (CardDTO each : cardList) {
            if (each.getCardId().equals(card.getCardId())) {
                cardList.set(cardList.indexOf(each), card);
                break;
            }
        }
    }
}
