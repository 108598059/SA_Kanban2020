package ddd.kanban.usecase.card.create;

import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.card.card.Card;
import ddd.kanban.usecase.card.mapper.CardEntityMapper;
import ddd.kanban.usecase.repository.CardRepository;

import java.util.UUID;

public class CreateCardUseCase {
    private CardRepository cardRepository;
    private DomainEventBus domainEventBus;

    public CreateCardUseCase(CardRepository cardRepository, DomainEventBus domainEventBus){
        this.cardRepository = cardRepository;
        this.domainEventBus = domainEventBus;
    }


    public void execute(CreateCardInput createCardInput, CreateCardOutput createCardOutput) {
        Card card = new Card(UUID.randomUUID().toString(), createCardInput.getCardTitle(), createCardInput.getBoardId(), createCardInput.getWorkflowId(), createCardInput.getColumnId());

        cardRepository.add(CardEntityMapper.mappingCardEntityFrom(card));

        domainEventBus.postAll(card);

        createCardOutput.setCardId(card.getId());
        createCardOutput.setCardTitle(card.getTitle());
    }
}
