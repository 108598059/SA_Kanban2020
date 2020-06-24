package phd.sa.csie.ntut.edu.tw.usecase.card.create;

import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.aggregate.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.card.CardRepository;

public class CreateCardUseCase extends UseCase<CreateCardUseCaseInput, CreateCardUseCaseOutput> {
    private CardRepository cardRepository;

    public CreateCardUseCase(DomainEventBus eventBus, CardRepository cardRepository) {
        super(eventBus);
        this.cardRepository = cardRepository;
    }

    @Override
    public void execute(CreateCardUseCaseInput input, CreateCardUseCaseOutput output) {

        Card card = new Card(input.getCardName(), input.getBoardID());

        this.cardRepository.save(CardDTOConverter.toDTO(card));
        this.eventBus.postAll(card);

        output.setCardName(card.getName());
        output.setCardID(card.getID().toString());
    }
}