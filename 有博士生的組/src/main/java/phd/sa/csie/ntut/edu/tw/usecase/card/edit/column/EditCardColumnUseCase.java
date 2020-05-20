package phd.sa.csie.ntut.edu.tw.usecase.card.edit.column;

import phd.sa.csie.ntut.edu.tw.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

import java.util.UUID;

public class EditCardColumnUseCase {
    private CardRepository cardRepository;

    public EditCardColumnUseCase(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void execute(EditCardColumnUseCaseInput input, EditCardColumnUseCaseOutput output) {
        Card card = CardDTOConverter.toEntity(this.cardRepository.findByID(input.getCardID()));

        card.setColumnID(UUID.fromString(input.getColumnID()));

        this.cardRepository.update(CardDTOConverter.toDTO(card));
        output.setColumnID(card.getColumnID().toString());
    }
}
