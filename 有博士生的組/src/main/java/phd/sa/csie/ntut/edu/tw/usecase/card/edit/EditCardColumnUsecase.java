package phd.sa.csie.ntut.edu.tw.usecase.card.edit;

import phd.sa.csie.ntut.edu.tw.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

import java.util.UUID;

public class EditCardColumnUsecase {
    private CardRepository cardRepository;

    public EditCardColumnUsecase(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void execute(EditCardColumnInput input, EditCardColumnOutput output) {
        Card card = CardDTOConverter.toEntity(this.cardRepository.findById(input.getCardID()));

        card.setColumnId(UUID.fromString(input.getColumnID()));

        this.cardRepository.update(CardDTOConverter.toDTO(card));
        output.setColumnID(card.getColumnId().toString());
    }
}
