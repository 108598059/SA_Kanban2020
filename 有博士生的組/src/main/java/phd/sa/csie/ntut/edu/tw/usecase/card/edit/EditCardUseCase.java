package phd.sa.csie.ntut.edu.tw.usecase.card.edit;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class EditCardUseCase {
  private CardRepository cardRepository;

  public EditCardUseCase(CardRepository repository) {
    this.cardRepository = repository;
  }

  public void execute(EditCardUseCaseInput input, EditCardUseCaseOutput output) {
    UUID cardID = input.getCardID();
    String cardName = input.getCardName();

    Card card = CardDTOConverter.toEntity(cardRepository.findByID(cardID.toString()));
    card.setName(cardName);

    CardDTO cardDTO = CardDTOConverter.toDTO(card);

    cardRepository.save(cardDTO);
    output.setCardID(card.getID().toString());
    output.setCardName(card.getName());
  }
}