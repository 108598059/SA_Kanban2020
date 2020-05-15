package phd.sa.csie.ntut.edu.tw.usecase.card.create;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class CreateCardUseCase extends UseCase<CreateCardUseCaseInput, CreateCardUseCaseOutput> {
  private CardRepository cardRepository;

  public CreateCardUseCase(CardRepository cardRepository, DomainEventBus eventBus) {
    super(eventBus);
    this.cardRepository = cardRepository;
  }

  @Override
  public void execute(CreateCardUseCaseInput input, CreateCardUseCaseOutput output) {
    String cardName = input.getCardName();
    UUID boardId = input.getBoardId();
    UUID columnId = input.getColumnId();
    Card card = new Card(cardName, boardId, columnId);

    cardRepository.save(CardDTOConverter.toDTO(card));
    this.eventBus.postAll(card);

    output.setCardName(card.getName());
    output.setCardId(card.getId());
  }

}