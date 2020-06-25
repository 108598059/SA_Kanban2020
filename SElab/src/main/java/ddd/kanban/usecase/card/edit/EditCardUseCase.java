package ddd.kanban.usecase.card.edit;

import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.card.card.Card;
import ddd.kanban.usecase.card.mapper.CardEntityMapper;
import ddd.kanban.usecase.repository.CardRepository;

public class EditCardUseCase {
    private CardRepository cardRepository;
    private DomainEventBus domainEventBus;

    public EditCardUseCase(CardRepository cardRepository, DomainEventBus domainEventBus) {
        this.cardRepository = cardRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(EditCardUseCaseInput editCardUseCaseInput, EditCardUseCaseOutput editCardUseCaseOutput) {
        Card card = CardEntityMapper.mappingCardFrom(cardRepository.findById( editCardUseCaseInput.getCardId()));
        card.setTitle(editCardUseCaseInput.getCardName());
        card.setDescription(editCardUseCaseInput.getCardDescription());
        card.setCardType(editCardUseCaseInput.getCardCardType());
        card.setTags(editCardUseCaseInput.getCardTags());
        card.setAssignUsers(editCardUseCaseInput.getCardAssignUsers());
        card.setPlannedStartDate(editCardUseCaseInput.getCardPlannedStartDate());
        card.setPlannedFinishDate(editCardUseCaseInput.getCardPlannedFinishDate());
        card.setPriority(editCardUseCaseInput.getCardPriority());

        cardRepository.save(CardEntityMapper.mappingCardEntityFrom(card));

        domainEventBus.postAll(card);

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
