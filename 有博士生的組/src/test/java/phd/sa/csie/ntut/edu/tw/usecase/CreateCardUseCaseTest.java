package phd.sa.csie.ntut.edu.tw.usecase;

import java.util.UUID;

import com.google.common.eventbus.Subscribe;

import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.domain.model.card.event.CardCreatedEvent;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CommitCardUsecase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

import static org.junit.Assert.*;

public class CreateCardUseCaseTest {

  private CardRepository cardRepository;
  private BoardRepository boardRepository;
  private BoardDTOConverter boardDTOConverter;
  private CommitCardUsecase commitCardUsecase;
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
    this.boardDTOConverter = new BoardDTOConverter();
    this.boardRepository.save(this.boardDTOConverter.toDTO(this.board));
    this.eventBus = new DomainEventBus();
  }

  @Test
  public void creating_a_new_card_should_commit_the_card_to_the_backlog_column() {
    this.commitCardUsecase = new CommitCardUsecase(this.cardRepository, this.boardRepository);
    this.eventBus.register(this.commitCardUsecase);

    CreateCardUseCase createCardUseCase = new CreateCardUseCase(this.eventBus);
    CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
    CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardUseCaseOutput();

    createCardUseCaseInput.setCardName("Create Card");
    createCardUseCaseInput.setBoardID(this.board.getId().toString());

    createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);

    assertEquals("Create Card", createCardUseCaseOutput.getCardName());
    assertNotEquals("", createCardUseCaseOutput.getCardId());
    assertNotNull(createCardUseCaseOutput.getCardId());

    UUID cardId = UUID.fromString(createCardUseCaseOutput.getCardId());
    CardDTO cardDTO = cardRepository.findById(cardId.toString());
    Card card = CardDTOConverter.toEntity(cardDTO);
    BoardDTO boardDTO = this.boardRepository.findById(this.board.getId().toString());
    BoardDTOConverter boardDTOConverter = new BoardDTOConverter();
    Board boardResult = boardDTOConverter.toEntity(boardDTO);
    assertTrue(boardResult.get(0).getCardIds().contains(card.getId()));
  }

  @Test
  public void committed_card_change_should_be_save_to_the_board_repository() {
    this.commitCardUsecase = new CommitCardUsecase(this.cardRepository, this.boardRepository);
    this.eventBus.register(commitCardUsecase);
    
    CreateCardUseCase createCardUseCase = new CreateCardUseCase(this.eventBus);
    CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
    CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardUseCaseOutput();

    createCardUseCaseInput.setCardName("Create Card");
    createCardUseCaseInput.setBoardID(this.board.getId().toString());
    createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);

    UUID cardId = UUID.fromString(createCardUseCaseOutput.getCardId());
    Card card = CardDTOConverter.toEntity(cardRepository.findById(cardId.toString()));
    assertEquals(this.board.get(0).getId().toString(), card.getColumnId().toString());
  }

  @Test
  public void create_card_event_should_be_issued_when_a_card_being_created() {
    CreateCardUseCase createCardUseCase = new CreateCardUseCase(this.eventBus);
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