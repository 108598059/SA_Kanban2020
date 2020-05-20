package phd.sa.csie.ntut.edu.tw.usecase.card.edit.name;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class EditCardNameUseCase {
  private CardRepository cardRepository;

  public EditCardNameUseCase(CardRepository repository) {
    this.cardRepository = repository;
  }

  public void execute(EditCardNameUseCaseInput input, EditCardNameUseCaseOutput output) {
    String cardID = input.getCardID();
    String cardName = input.getCardName();

    Card card = CardDTOConverter.toEntity(this.cardRepository.findByID(cardID));
    card.setName(cardName);

    this.cardRepository.save(CardDTOConverter.toDTO(card));
    output.setCardID(card.getID().toString());
    output.setCardName(card.getName());
  }
}