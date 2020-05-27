package phd.sa.csie.ntut.edu.tw.usecase.card.create;

import com.google.common.eventbus.Subscribe;

import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.adapter.presenter.card.create.CreateCardPresenter;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.board.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.card.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.Card;
import phd.sa.csie.ntut.edu.tw.model.card.event.create.CardCreatedEvent;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.card.CardCreatedEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.card.CardRepository;

import java.util.UUID;

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

    this.board = new Board(UUID.randomUUID(), "Kanban");
    this.boardRepository.save(BoardDTOConverter.toDTO(this.board));

    this.eventBus = new DomainEventBus();
    CardCreatedEventHandler cardCreatedEventHandler = new CardCreatedEventHandler(this.eventBus, this.cardRepository, this.boardRepository);
    this.eventBus.register(cardCreatedEventHandler);
  }

  @Test
  public void create_card_should_commit_the_card_to_the_backlog_column() {
    CreateCardUseCase createCardUseCase = new CreateCardUseCase(this.eventBus, this.cardRepository, this.boardRepository);
    CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
    CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardPresenter();

    createCardUseCaseInput.setCardName("Create Card");
    createCardUseCaseInput.setBoardID(this.board.getID().toString());

    createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);

    assertEquals("Create Card", createCardUseCaseOutput.getCardName());
    assertNotEquals("", createCardUseCaseOutput.getCardID());
    assertNotNull(createCardUseCaseOutput.getCardID());

    Card card = CardDTOConverter.toEntity(this.cardRepository.findByID(createCardUseCaseOutput.getCardID()));
    Board boardResult = BoardDTOConverter.toEntity(this.boardRepository.findByID(this.board.getID().toString()));
    assertEquals(card.getID(), boardResult.getBacklogColumn().getCardIDs().get(0));
  }

  @Test
  public void created_card_change_should_be_save_to_the_board_repository() {
    CreateCardUseCase createCardUseCase = new CreateCardUseCase(this.eventBus, this.cardRepository, this.boardRepository);
    CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
    CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardPresenter();

    createCardUseCaseInput.setCardName("Create Card");
    createCardUseCaseInput.setBoardID(this.board.getID().toString());

    createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);

    Card card = CardDTOConverter.toEntity(this.cardRepository.findByID(createCardUseCaseOutput.getCardID()));
    assertEquals(this.board.getBacklogColumn().getID().toString(), card.getBelongsColumnID().toString());
  }

  @Test
  public void create_card_event_should_be_posted_when_a_card_being_created() {
    CreateCardUseCase createCardUseCase = new CreateCardUseCase(this.eventBus, this.cardRepository, this.boardRepository);
    CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
    CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardPresenter();

    createCardUseCaseInput.setCardName("Create Card");
    createCardUseCaseInput.setBoardID(this.board.getID().toString());

    MockCardCreatedEventListener mockListener = new MockCardCreatedEventListener();
    this.eventBus.register(mockListener);

    createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);

    assertEquals(1, mockListener.getEventCount());
  }
}