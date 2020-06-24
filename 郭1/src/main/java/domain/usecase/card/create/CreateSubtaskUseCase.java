package domain.usecase.card.create;

import domain.entity.DomainEventBus;
import domain.entity.aggregate.card.Card;
import domain.usecase.card.CardRepository;
import domain.usecase.card.CardTransformer;

public class CreateSubtaskUseCase {

    private CardRepository cardRepository;
    private DomainEventBus eventBus;

    public CreateSubtaskUseCase(CardRepository cardRepository, DomainEventBus eventBus){
        this.cardRepository = cardRepository;
        this.eventBus = eventBus;

    }
    public void execute(CreateSubtaskInput createSubtaskInput, CreateSubtaskOutput createSubtaskOutput) {

        Card card = CardTransformer.toCard(cardRepository.getCardById(createSubtaskInput.getCardId()));
        String task_Id = card.createSubtask(createSubtaskInput.getSubtaskName());

        cardRepository.save(CardTransformer.toDTO(card));
        eventBus.postAll(card);

        createSubtaskOutput.setSubtaskId(task_Id);

    }
}
