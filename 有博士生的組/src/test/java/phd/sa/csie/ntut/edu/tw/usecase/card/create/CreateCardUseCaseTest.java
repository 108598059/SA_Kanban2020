package phd.sa.csie.ntut.edu.tw.usecase.card.create;

import com.google.common.eventbus.Subscribe;

import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.Card;
import phd.sa.csie.ntut.edu.tw.model.card.event.CardCreatedEvent;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.CardCreatedEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

import static org.junit.Assert.*;

public class CreateCardUseCaseTest {

  private CardRepository cardRepository;
  private BoardRepository boardRepository;
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
    this.cardRepository = new MemoryCardRepository();
    this.boardRepository = new MemoryBoardRepository();

    this.board = new Board("Kanban");
    this.boardRepository.save(BoardDTOConverter.toDTO(this.board));

    CardCreatedEventHandler cardCreatedEventHandler = new CardCreatedEventHandler(this.cardRepository, this.boardRepository);
    this.eventBus = new DomainEventBus();
    this.eventBus.register(cardCreatedEventHandler);

  }

  @Test
  public void creating_a_new_card_should_commit_the_card_to_the_backlog_column() {
    CreateCardUseCase createCardUseCase = new CreateCardUseCase(this.eventBus, this.cardRepository, this.boardRepository);
    CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
    CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardUseCaseOutput();

    createCardUseCaseInput.setCardName("Create Card");
    createCardUseCaseInput.setBoardID(this.board.getId().toString());

    createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);

    assertEquals("Create Card", createCardUseCaseOutput.getCardName());
    assertNotEquals("", createCardUseCaseOutput.getCardId());
    assertNotNull(createCardUseCaseOutput.getCardId());

    Card card = CardDTOConverter.toEntity(cardRepository.findById(createCardUseCaseOutput.getCardId()));
    Board boardResult = BoardDTOConverter.toEntity(this.boardRepository.findById(this.board.getId().toString()));
    assertEquals(card.getId(), boardResult.get(0).getCardIds().get(0));
  }

  @Test
  public void committed_card_change_should_be_save_to_the_board_repository() {
    CreateCardUseCase createCardUseCase = new CreateCardUseCase(this.eventBus, this.cardRepository, this.boardRepository);
    CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
    CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardUseCaseOutput();

    createCardUseCaseInput.setCardName("Create Card");
    createCardUseCaseInput.setBoardID(this.board.getId().toString());

    createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);

    Card card = CardDTOConverter.toEntity(cardRepository.findById(createCardUseCaseOutput.getCardId()));
    assertEquals(this.board.get(0).getId().toString(), card.getColumnId().toString());
  }

  @Test
  public void create_card_event_should_be_issued_when_a_card_being_created() {
    CreateCardUseCase createCardUseCase = new CreateCardUseCase(this.eventBus, this.cardRepository, this.boardRepository);
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