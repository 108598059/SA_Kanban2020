package phd.sa.csie.ntut.edu.tw.usecase.card.edit;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class EditCardUseCase {

  private CardRepository cardRepository;

  public EditCardUseCase(CardRepository repository) {
    this.cardRepository = repository;
  }

  public void execute(EditCardUseCaseInput input, EditCardUseCaseOutput output) {
    UUID cardId = input.getCardId();
    String cardName = input.getCardName();

    Card card = cardRepository.findCardByUUID(cardId);
    card.setName(cardName);

    cardRepository.add(card);
    output.setCardId(card.getId().toString());
    output.setCardName(card.getName());
  }

}