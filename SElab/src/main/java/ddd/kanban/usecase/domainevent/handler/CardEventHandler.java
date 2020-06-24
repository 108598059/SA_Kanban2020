package ddd.kanban.usecase.domainevent.handler;

import com.google.common.eventbus.Subscribe;
import ddd.kanban.domain.model.card.Card;
import ddd.kanban.domain.model.card.event.CardMoved;
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
        card.setLaneId(cardMoved.getToLaneId());
        cardRepository.save(CardEntityMapper.mappingCardEntityFrom(card));
    }
}
