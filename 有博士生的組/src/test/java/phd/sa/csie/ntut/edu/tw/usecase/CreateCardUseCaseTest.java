package phd.sa.csie.ntut.edu.tw.usecase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.UUID;

import com.google.common.eventbus.Subscribe;

import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.domain.model.card.event.CardCreatedEvent;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CardCreatedEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class CreateCardUseCaseTest {

  private CardRepository cardRepository;
  private CardDTOConverter cardDTOConverter;
  private CardCreatedEventHandler cardCreatedEventHandler;
  private Board board;
  private DomainEventBus eventBus;

  private class MockCardCreatedEventListener {

    private int count = 0;

    @Subscribe
    public void cardCreatedListener(CardCreatedEvent e) {
      this.count += 1;
    }

    public int getEventCount() {
      return this.count;
    }
  }

  @Before
  public void given_a_board() {
    this.cardRepository = new MemoryCardRepository(new HashMap<UUID, CardDTO>());
    this.board = new Board("Kanban");
    this.eventBus = new DomainEventBus();
    this.cardDTOConverter = new CardDTOConverter();
  }

  @Test
  public void creating_a_new_card_should_commit_the_card_to_the_backlog_column() {
    this.cardCreatedEventHandler = new CardCreatedEventHandler(this.cardRepository);
    this.eventBus.register(this.board);
    this.eventBus.register(cardCreatedEventHandler);

    CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, this.eventBus, this.cardDTOConverter);
    CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
    CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardUseCaseOutput();

    createCardUseCaseInput.setCardName("Create Card");
    createCardUseCaseInput.setBoardID(this.board.getId().toString());

    createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);

    assertEquals("Create Card", createCardUseCaseOutput.getCardName());
    assertNotEquals("", createCardUseCaseOutput.getCardId());
    assertNotNull(createCardUseCaseOutput.getCardId());

    UUID cardId = UUID.fromString(createCardUseCaseOutput.getCardId());
    CardDTO cardDTO = cardRepository.findById(cardId);
    Card card = cardDTOConverter.toEntity(cardDTO);
    assertEquals(this.board.get(0).getId().toString(), card.getColumnId().toString());
  }

  @Test
  public void committed_card_change_should_be_save_to_the_board_repository() {
    this.cardCreatedEventHandler = new CardCreatedEventHandler(this.cardRepository);
    this.eventBus.register(this.board);
    this.eventBus.register(cardCreatedEventHandler);
    
    CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, this.eventBus, this.cardDTOConverter);
    CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
    CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardUseCaseOutput();

    createCardUseCaseInput.setCardName("Create Card");
    createCardUseCaseInput.setBoardID(this.board.getId().toString());
    createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);

    UUID cardId = UUID.fromString(createCardUseCaseOutput.getCardId());
    Card card = cardDTOConverter.toEntity(cardRepository.findById(cardId));
    assertEquals(this.board.get(0).getId().toString(), card.getColumnId().toString());
  }

  @Test
  public void create_card_event_should_be_issued_when_a_card_being_created() {
    CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, this.eventBus, this.cardDTOConverter);
    CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
    CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardUseCaseOutput();

    createCardUseCaseInput.setCardName("Create Card");
    createCardUseCaseInput.setBoardID(this.board.getId().toString());

    MockCardCreatedEventListener mockListener = new MockCardCreatedEventListener();
    this.eventBus.register(mockListener);

    createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);
    assertEquals(1, mockListener.getEventCount());
  }
}