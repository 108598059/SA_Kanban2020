package phd.sa.csie.ntut.edu.tw.usecase.card.edit;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class EditCardUseCase {

  private CardRepository cardRepository;
  private CardDTOConverter cardDTOConverter;

  public EditCardUseCase(CardRepository repository) {
    this.cardRepository = repository;
    this.cardDTOConverter = new CardDTOConverter();
  }

  public void execute(EditCardUseCaseInput input, EditCardUseCaseOutput output) {
    UUID cardId = input.getCardId();
    String cardName = input.getCardName();

    Card card = cardDTOConverter.toEntity(cardRepository.findById(cardId));
    card.setName(cardName);

    CardDTO cardDTO = cardDTOConverter.toDTO(card);

    cardRepository.update(cardDTO);
    output.setCardId(card.getId().toString());
    output.setCardName(card.getName());
  }

}