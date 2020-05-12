package phd.sa.csie.ntut.edu.tw.usecase.card.create;

import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class CreateCardUseCase extends UseCase<CardRepository, CardDTOConverter, CreateCardUseCaseInput, CreateCardUseCaseOutput> {
  // private CardRepository cardRepository;
  // private DomainEventBus eventBus;

  public CreateCardUseCase(CardRepository cardRepository, DomainEventBus eventBus, CardDTOConverter cardDTOConverter) {
    super(cardRepository, eventBus, cardDTOConverter);
    // this.cardRepository = cardRepository;
    // this.eventBus = eventBus;
  }

  @Override
  public void execute(CreateCardUseCaseInput createCardInput, CreateCardUseCaseOutput createCardOutput) {
    String cardName = createCardInput.getCardName();
    Card card = new Card(cardName); 
    
    CardDTO cardDTO = this.dtoConverter.toDTO(card);
    this.repository.save(cardDTO);
    // TODO Should there be another object responsible for when to post the events?
    this.eventBus.postAll(card);

    createCardOutput.setCardName(card.getName());
    createCardOutput.setCardId(card.getId());
  }

}