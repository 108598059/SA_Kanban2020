package domain.usecase.task.create;

import domain.model.aggregate.DomainEventBus;
import domain.model.aggregate.card.Card;
import domain.model.aggregate.card.Task;
import domain.usecase.card.repository.ICardRepository;

public class CreateTaskUseCase {
    private ICardRepository cardRepository;
    private Task task;
    private DomainEventBus eventBus;

    public CreateTaskUseCase(ICardRepository cardRepository,DomainEventBus eventBus) {
        this.cardRepository = cardRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateTaskUseCaseInput input, CreateTaskUseCaseOutput output) {
        Card card = cardRepository.getCardById(input.getCardId());
        task = card.createTask(input.getCardId(), input.getTaskName());

        card.addTaskId(task);

        cardRepository.save(card);
        eventBus.postAll(card);

        output.setCardId(task.getCardId());
        output.setTaskId(task.getTaskId());
        output.setTaskName(task.getTaskName());
    }
}
