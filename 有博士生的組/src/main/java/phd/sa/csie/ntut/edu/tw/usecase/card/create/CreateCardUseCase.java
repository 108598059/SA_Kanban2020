package phd.sa.csie.ntut.edu.tw.usecase.card.create;

import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

import java.util.UUID;

public class CreateCardUseCase {
  private CardRepository cardRepository;
  private DomainEventBus eventBus;

  public CreateCardUseCase(CardRepository cardRepository, DomainEventBus eventBus) {
    this.cardRepository = cardRepository;
    this.eventBus = eventBus;
  }

  public void execute(CreateCardUseCaseInput createCardInput, CreateCardUseCaseOutput createCardOutput) {
    String cardName = createCardInput.getCardName();

    Card card = new Card(cardName);
    cardRepository.add(card);

    this.eventBus.postAll(card);
    createCardOutput.setCardName(card.getName());
    createCardOutput.setCardId(card.getUUID());
  }

}