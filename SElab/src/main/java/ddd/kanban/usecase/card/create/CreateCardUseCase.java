package ddd.kanban.usecase.card.create;

import ddd.kanban.domain.model.card.Card;
import ddd.kanban.usecase.repository.CardRepository;

import java.util.UUID;

public class CreateCardUseCase {
    private CardRepository cardRepository;

    public CreateCardUseCase(CardRepository cardRepository){
        this.cardRepository = cardRepository;
    }


    public void execute(CreateCardInput createCardInput, CreateCardOutput createCardOutput) {
        Card card = new Card(UUID.randomUUID().toString(),
                            createCardInput.getCardTitle(),
                            createCardInput.getCardDescription(),
                            createCardInput.getCardCardType(),
                            createCardInput.getCardTags(),
                            createCardInput.getCardAssignUsers(),
                            createCardInput.getCardPlannedStartDate(),
                            createCardInput.getCardPlannedFinishDate(),
                            createCardInput.getCardPriority());

        cardRepository.add(card);
        cardRepository.save(card);

        Card outputCard = cardRepository.findById(card.getId());

        createCardOutput.setCardId(outputCard.getId());
        createCardOutput.setCardTitle(outputCard.getTitle());
        createCardOutput.setCardDescription(outputCard.getDescription());
        createCardOutput.setCardCardType(outputCard.getCardType());
        createCardOutput.setCardTags(outputCard.getTags());
        createCardOutput.setCardAssignUsers(outputCard.getAssignUsers());
        createCardOutput.setCardPlannedStartDate(outputCard.getPlannedStartDate());
        createCardOutput.setCardPlannedFinishDate(outputCard.getPlannedFinishDate());
        createCardOutput.setCardPriority(outputCard.getPriority());
    }
}
