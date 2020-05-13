package ddd.kanban.usecase.card.create;

import ddd.kanban.domain.model.card.Card;
import ddd.kanban.domain.model.card.Task;
import ddd.kanban.usecase.repository.CardRepository;

public class CreateTaskUseCase {
    private CardRepository cardRepository;

    public CreateTaskUseCase(CardRepository cardRepository){
        this.cardRepository = cardRepository;
    }

    public void execute(CreateTaskInput createTaskInput, CreateTaskOutput createTaskOutput) {
        Card card = cardRepository.findById(createTaskInput.getCardId());

        String taskId = card.createTask(createTaskInput.getTaskTitle(), createTaskInput.getCardId());

        cardRepository.save(card);

        createTaskOutput.setTaskTitle(createTaskInput.getTaskTitle());
        createTaskOutput.setTaskId(taskId);
        createTaskOutput.setCardId(card.getId());
    }
}
