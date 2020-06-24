package domain.usecase.task.create;

import domain.model.DomainEventBus;
import domain.model.aggregate.card.Card;
import domain.model.aggregate.card.Task;
import domain.usecase.card.CardTransfer;
import domain.usecase.card.repository.ICardRepository;

public class CreateTaskUseCase {
    private ICardRepository cardRepository;
    private Task task;
    private DomainEventBus eventBus;

    public CreateTaskUseCase(ICardRepository cardRepository,DomainEventBus eventBus) {
        this.cardRepository = cardRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateTaskUseCaseInput input, CreateTaskUseCaseOutput output) throws CloneNotSupportedException {
        Card card = CardTransfer.CardDTOToCard(cardRepository.getCardById(input.getCardId()));
        task = card.createTask(input.getCardId(), input.getTaskName());

        card.addTaskId(task);

        cardRepository.save(CardTransfer.CardToCardDTO(card));
        eventBus.postAll(card);

        output.setCardId(task.getCardId());
        output.setTaskId(task.getTaskId());
        output.setTaskName(task.getTaskName());
    }
}
