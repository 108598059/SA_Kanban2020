package phd.sa.csie.ntut.edu.tw.usecase;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.EditCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.EditCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.EditCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class EditCardUseCaseTest {

  private Card card;
  private CardRepository cardRepository;
  private CreateCardUseCase createCardUseCase;
  private DomainEventBus eventBus;

  @Before
  public void given_a_card() {
    this.eventBus = new DomainEventBus();

    cardRepository = new MemoryCardRepository();
    createCardUseCase = new CreateCardUseCase(cardRepository, eventBus);

    CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
    CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardUseCaseOutput();
    createCardUseCaseInput.setCardName("Old Name");
    createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);
    card = cardRepository.findCardByUUID(UUID.fromString(createCardUseCaseOutput.getCardId()));
  }

  @Test
  public void editCard() {
    EditCardUseCase editCardUseCase = new EditCardUseCase(cardRepository);
    EditCardUseCaseInput editCardUseCaseInput = new EditCardUseCaseInput();
    EditCardUseCaseOutput editCardUseCaseOutput = new EditCardUseCaseOutput();
    editCardUseCaseInput.setCardId(card.getUUID());
    editCardUseCaseInput.setCardName("New Name");
    editCardUseCase.execute(editCardUseCaseInput, editCardUseCaseOutput);
    assertEquals(card.getUUID().toString(), editCardUseCaseOutput.getCardId());
    assertEquals("New Name", editCardUseCaseOutput.getCardName());
  }

}