package domain.usecase.task.create;

import domain.entity.card.Card;
import domain.usecase.card.CardRepository;

public class CreateTaskUseCase {
    private CardRepository cardRepository;

    public CreateTaskUseCase(CardRepository cardRepository){
        this.cardRepository = cardRepository;


    }
    public void execute(CreateTaskInput createTaskInput, CreateTaskOutput createTaskOutput) {
        Card card = cardRepository.get(createTaskInput.getCardId());
        String task_Id = card.createTask(createTaskInput.getTaskName());
        System.out.println(task_Id);
        cardRepository.save(card);
        createTaskOutput.setTaskId(task_Id);

    }
}
