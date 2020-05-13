package phd.sa.csie.ntut.edu.tw.usecase;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.EditCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.EditCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.EditCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class EditCardUseCaseTest {

  private Card card;
  private CardRepository cardRepository;
  private CreateCardUseCase createCardUseCase;
  private DomainEventBus eventBus;
  private CardDTOConverter cardDTOConverter;

  @Before
  public void given_a_card() {
    this.eventBus = new DomainEventBus();
    this.cardDTOConverter = new CardDTOConverter();

    cardRepository = new MemoryCardRepository(new HashMap<UUID, CardDTO>());
    createCardUseCase = new CreateCardUseCase(cardRepository, eventBus, cardDTOConverter);
    cardDTOConverter = new CardDTOConverter();

    CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
    CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardUseCaseOutput();
    createCardUseCaseInput.setCardName("Old Name");
    createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);
    UUID cardId = UUID.fromString(createCardUseCaseOutput.getCardId());
    card = cardDTOConverter.toEntity(cardRepository.findById(cardId));
  }

  @Test
  public void editCard() {
    EditCardUseCase editCardUseCase = new EditCardUseCase(cardRepository);
    EditCardUseCaseInput editCardUseCaseInput = new EditCardUseCaseInput();
    EditCardUseCaseOutput editCardUseCaseOutput = new EditCardUseCaseOutput();

    editCardUseCaseInput.setCardId(card.getId());
    editCardUseCaseInput.setCardName("New Name");
    editCardUseCase.execute(editCardUseCaseInput, editCardUseCaseOutput);

    assertEquals(card.getId().toString(), editCardUseCaseOutput.getCardId());
    assertEquals("New Name", editCardUseCaseOutput.getCardName());
  }

}