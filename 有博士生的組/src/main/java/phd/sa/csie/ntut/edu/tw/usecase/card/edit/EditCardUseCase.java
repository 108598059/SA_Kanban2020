package phd.sa.csie.ntut.edu.tw.usecase.card.edit;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.DTO;
import phd.sa.csie.ntut.edu.tw.usecase.DTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class EditCardUseCase {

  private CardRepository cardRepository;
  private DTOConverter dtoConverter;

  public EditCardUseCase(CardRepository repository) {
    this.cardRepository = repository;
  }

  public void execute(EditCardUseCaseInput input, EditCardUseCaseOutput output) {
    UUID cardId = input.getCardId();
    String cardName = input.getCardName();

    Card card = (Card) dtoConverter.toEntity(cardRepository.findById(cardId));
    card.setName(cardName);

    DTOConverter dtoConverter = new DTOConverter();
    DTO cardDTO = dtoConverter.toDTO(card);

    cardRepository.add(cardDTO);
    output.setCardId(card.getId().toString());
    output.setCardName(card.getName());
  }

}