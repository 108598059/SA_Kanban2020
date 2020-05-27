package domain.usecase.subtask.create;

import domain.entity.DomainEventBus;
import domain.entity.card.Card;
import domain.usecase.card.CardRepository;

public class CreateSubtaskUseCase {

    private CardRepository cardRepository;
    private DomainEventBus eventBus;

    public CreateSubtaskUseCase(CardRepository cardRepository, DomainEventBus eventBus){
        this.cardRepository = cardRepository;
        this.eventBus = eventBus;

    }
    public void execute(CreateSubtaskInput createSubtaskInput, CreateSubtaskOutput createSubtaskOutput) {

        Card card = cardRepository.getCardById(createSubtaskInput.getCardId());
        String task_Id = card.createSubtask(createSubtaskInput.getSubtaskName());

        cardRepository.save(card);
        eventBus.postAll(card);

        createSubtaskOutput.setSubtaskId(task_Id);

    }
}
