package phd.sa.csie.ntut.edu.tw.usecase.card.edit;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class EditCardUseCase {
  private CardRepository cardRepository;

  public EditCardUseCase(CardRepository repository) {
    this.cardRepository = repository;
  }

  public void execute(EditCardUseCaseInput input, EditCardUseCaseOutput output) {
    UUID cardId = input.getCardId();
    String cardName = input.getCardName();

    Card card = CardDTOConverter.toEntity(cardRepository.findById(cardId.toString()));
    card.setName(cardName);

    CardDTO cardDTO = CardDTOConverter.toDTO(card);

    cardRepository.save(cardDTO);
    output.setCardId(card.getId().toString());
    output.setCardName(card.getName());
  }
}