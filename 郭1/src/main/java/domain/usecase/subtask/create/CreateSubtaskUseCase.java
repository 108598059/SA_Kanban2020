package domain.usecase.subtask.create;

import domain.entity.card.Card;
import domain.usecase.card.CardRepository;

public class CreateSubtaskUseCase {
    private CardRepository cardRepository;

    public CreateSubtaskUseCase(CardRepository cardRepository){
        this.cardRepository = cardRepository;


    }
    public void execute(CreateSubtaskInput createSubtaskInput, CreateSubtaskOutput createSubtaskOutput) {
        Card card = cardRepository.getCardById(createSubtaskInput.getCardId());
        String task_Id = card.createSubtask(createSubtaskInput.getSubtaskName());
        System.out.println(task_Id);
        cardRepository.save(card);
        createSubtaskOutput.setSubtaskId(task_Id);

    }
}
