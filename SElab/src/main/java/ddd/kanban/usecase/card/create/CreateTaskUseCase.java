package ddd.kanban.usecase.card.create;

import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.card.Card;
import ddd.kanban.domain.model.card.Task;
import ddd.kanban.usecase.card.mapper.CardEntityMapper;
import ddd.kanban.usecase.repository.CardRepository;

public class CreateTaskUseCase {
    private CardRepository cardRepository;
    private DomainEventBus domainEventBus;

    public CreateTaskUseCase(CardRepository cardRepository, DomainEventBus domainEventBus){
        this.cardRepository = cardRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(CreateTaskInput createTaskInput, CreateTaskOutput createTaskOutput) {
        Card card = CardEntityMapper.mappingCardFrom(cardRepository.findById(createTaskInput.getCardId()));

        String taskId = card.createTask(createTaskInput.getTaskTitle(), createTaskInput.getCardId());

        cardRepository.save(CardEntityMapper.mappingCardEntityFrom(card));

        domainEventBus.postAll(card);

        createTaskOutput.setTaskTitle(createTaskInput.getTaskTitle());
        createTaskOutput.setTaskId(taskId);
        createTaskOutput.setCardId(card.getId());
    }
}
