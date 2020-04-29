package ddd.kanban.usecase.card.edit;

import ddd.kanban.domain.model.card.Card;
import ddd.kanban.usecase.repository.CardRepository;

public class EditCardUseCase {
    private CardRepository cardRepository;

    public EditCardUseCase(CardRepository cardRepository) { this.cardRepository = cardRepository; }

    public void execute(EditCardUseCaseInput editCardUseCaseInput, EditCardUseCaseOutput editCardUseCaseOutput) {
        Card card = cardRepository.findById( editCardUseCaseInput.getCardId() );
        card.setTitle(editCardUseCaseInput.getCardName());
        card.setDescription(editCardUseCaseInput.getCardDescription());
        card.setCardType(editCardUseCaseInput.getCardCardType());
        card.setTags(editCardUseCaseInput.getCardTags());
        card.setAssignUsers(editCardUseCaseInput.getCardAssignUsers());
        card.setPlannedStartDate(editCardUseCaseInput.getCardPlannedStartDate());
        card.setPlannedFinishDate(editCardUseCaseInput.getCardPlannedFinishDate());
        card.setPriority(editCardUseCaseInput.getCardPriority());
        cardRepository.update(card);
        cardRepository.save(card);

        Card outputCard = cardRepository.findById( editCardUseCaseInput.getCardId() );

        editCardUseCaseOutput.setCardId(outputCard.getId());
        editCardUseCaseOutput.setCardName(outputCard.getTitle());
        editCardUseCaseOutput.setCardDescription(outputCard.getDescription());
        editCardUseCaseOutput.setCardCardType(outputCard.getCardType());
        editCardUseCaseOutput.setCardTags(outputCard.getTags());
        editCardUseCaseOutput.setCardAssignUsers(outputCard.getAssignUsers());
        editCardUseCaseOutput.setCardPlannedStartDate(outputCard.getPlannedStartDate());
        editCardUseCaseOutput.setCardPlannedFinishDate(outputCard.getPlannedFinishDate());
        editCardUseCaseOutput.setCardPriority(outputCard.getPriority());
    }
}
