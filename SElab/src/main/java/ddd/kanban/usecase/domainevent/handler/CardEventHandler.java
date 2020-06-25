package ddd.kanban.usecase.domainevent.handler;

import com.google.common.eventbus.Subscribe;
import ddd.kanban.domain.model.card.card.Card;
import ddd.kanban.domain.model.card.card.event.CardMoved;
import ddd.kanban.usecase.card.mapper.CardEntityMapper;
import ddd.kanban.usecase.repository.CardRepository;

public class CardEventHandler {
    private CardRepository cardRepository;

    public CardEventHandler(CardRepository cardRepository){
        this.cardRepository = cardRepository;
    }

    @Subscribe
    public void handleDoaminEvent(CardMoved cardMoved){
        Card card = CardEntityMapper.mappingCardFrom(cardRepository.findById(cardMoved.getSourceId()));
        card.setColumnId(cardMoved.getToColumnId());
        cardRepository.save(CardEntityMapper.mappingCardEntityFrom(card));
    }
}
