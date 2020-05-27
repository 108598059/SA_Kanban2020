package phd.sa.csie.ntut.edu.tw.usecase.card.edit.column;

import phd.sa.csie.ntut.edu.tw.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

import java.util.UUID;

public class EditCardBelongsColumnUseCase extends UseCase<EditCardBelongsColumnUseCaseInput, EditCardBelongsColumnUseCaseOutput> {
    private CardRepository cardRepository;

    public EditCardBelongsColumnUseCase(DomainEventBus eventBus, CardRepository cardRepository) {
        super(eventBus);
        this.cardRepository = cardRepository;
    }

    public void execute(EditCardBelongsColumnUseCaseInput input, EditCardBelongsColumnUseCaseOutput output) {
        Card card = CardDTOConverter.toEntity(this.cardRepository.findByID(input.getCardID()));

        card.setBelongsColumnID(UUID.fromString(input.getColumnID()));

        this.cardRepository.update(CardDTOConverter.toDTO(card));
        this.eventBus.postAll(card);
        output.setColumnID(card.getBelongsColumnID().toString());
    }
}
