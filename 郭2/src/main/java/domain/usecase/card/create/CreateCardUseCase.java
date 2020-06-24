package domain.usecase.card.create;

import domain.model.DomainEventBus;
import domain.model.aggregate.card.Card;
import domain.usecase.card.repository.ICardRepository;


public class CreateCardUseCase {
    private ICardRepository cardRepository;
    private DomainEventBus eventBus;

    public CreateCardUseCase(ICardRepository cardRepository, DomainEventBus eventBus){
        this.eventBus = eventBus;
        this.cardRepository = cardRepository;
    }

    public void execute(CreateCardUseCaseInput createCardUseCaseInput, CreateCardUseCaseOutput createCardUseCaseOutput) {
        Card card = new Card(createCardUseCaseInput.getCardName(), createCardUseCaseInput.getWorkflowId(), createCardUseCaseInput.getLaneId());
        card.setCardContent(createCardUseCaseInput.getCardContent());
        card.setCardType(createCardUseCaseInput.getCardType());

        cardRepository.add(card);

        eventBus.postAll(card);

        createCardUseCaseOutput.setCardName(card.getCardName());
        createCardUseCaseOutput.setCardId(card.getCardId());
        createCardUseCaseOutput.setCardContent(card.getCardContent());
        createCardUseCaseOutput.setCardType(card.getCardType());
    }
}
