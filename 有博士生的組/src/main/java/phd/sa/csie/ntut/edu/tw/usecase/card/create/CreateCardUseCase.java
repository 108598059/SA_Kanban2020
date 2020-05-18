package phd.sa.csie.ntut.edu.tw.usecase.card.create;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phd.sa.csie.ntut.edu.tw.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;

import java.util.UUID;

@Service
public class CreateCardUseCase extends UseCase<CreateCardUseCaseInput, CreateCardUseCaseOutput> {
  public CreateCardUseCase(@Autowired DomainEventBus eventBus){
    super(eventBus);
  }

  @Override
  public void execute(CreateCardUseCaseInput createCardInput, CreateCardUseCaseOutput createCardOutput) {
    Card card = new Card(createCardInput.getCardName(), UUID.fromString(createCardInput.getBoardID()));
    
    this.eventBus.postAll(card);

    createCardOutput.setCardName(card.getName());
    createCardOutput.setCardId(card.getId());
  }

}