package phd.sa.csie.ntut.edu.tw.usecase.card.create;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

import java.util.UUID;

@Service
public class CreateCardUseCase extends UseCase<CreateCardUseCaseInput, CreateCardUseCaseOutput> {
   private CardRepository cardRepository;
   private CardDTOConverter cardDTOConverter;

  public CreateCardUseCase(@Autowired CardRepository cardRepository, @Autowired DomainEventBus eventBus, @Autowired CardDTOConverter cardDTOConverter) {
    super(eventBus);
    this.cardRepository = cardRepository;
    this.cardDTOConverter = cardDTOConverter;
  }

  @Override
  public void execute(CreateCardUseCaseInput createCardInput, CreateCardUseCaseOutput createCardOutput) {
    Card card = new Card(createCardInput.getCardName(), UUID.fromString(createCardInput.getBoardID()));
    
    CardDTO cardDTO = this.cardDTOConverter.toDTO(card);
    this.cardRepository.save(cardDTO);
    // TODO Should there be another object responsible for when to post the events?
    this.eventBus.postAll(card);

    createCardOutput.setCardName(card.getName());
    createCardOutput.setCardId(card.getId());
  }

}