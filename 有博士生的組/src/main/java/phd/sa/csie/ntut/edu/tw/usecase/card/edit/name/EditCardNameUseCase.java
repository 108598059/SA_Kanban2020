package phd.sa.csie.ntut.edu.tw.usecase.card.edit.name;

import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.aggregate.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.card.CardRepository;

public class EditCardNameUseCase extends UseCase<EditCardNameUseCaseInput, EditCardNameUseCaseOutput> {
    private final CardRepository cardRepository;

    public EditCardNameUseCase(DomainEventBus eventBus, CardRepository repository) {
        super(eventBus);
        this.cardRepository = repository;
    }

    public void execute(EditCardNameUseCaseInput input, EditCardNameUseCaseOutput output) {
        String cardID = input.getCardID();
        String cardName = input.getCardName();

        Card card = CardDTOConverter.toEntity(this.cardRepository.findByID(cardID));
        card.updateName(cardName);

        this.cardRepository.save(CardDTOConverter.toDTO(card));
        this.eventBus.postAll(card);

        output.setCardID(card.getID().toString());
        output.setCardName(card.getName());
    }
}