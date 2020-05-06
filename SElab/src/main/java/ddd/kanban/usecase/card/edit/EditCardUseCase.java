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

        cardRepository.save(card);

        editCardUseCaseOutput.setCardId(card.getId());
        editCardUseCaseOutput.setCardName(card.getTitle());
        editCardUseCaseOutput.setCardDescription(card.getDescription());
        editCardUseCaseOutput.setCardCardType(card.getCardType());
        editCardUseCaseOutput.setCardTags(card.getTags());
        editCardUseCaseOutput.setCardAssignUsers(card.getAssignUsers());
        editCardUseCaseOutput.setCardPlannedStartDate(card.getPlannedStartDate());
        editCardUseCaseOutput.setCardPlannedFinishDate(card.getPlannedFinishDate());
        editCardUseCaseOutput.setCardPriority(card.getPriority());
    }
}
