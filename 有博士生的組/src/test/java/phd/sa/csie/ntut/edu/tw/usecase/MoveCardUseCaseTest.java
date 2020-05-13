package phd.sa.csie.ntut.edu.tw.usecase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryEventLogRepository;
import phd.sa.csie.ntut.edu.tw.domain.model.DomainEvent;
import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CardCreatedEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.move.MoveCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.move.MoveCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.move.MoveCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.column.setwip.SetColumnWIPUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.column.setwip.SetColumnWIPUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.column.setwip.SetColumnWIPUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.EventLogRepository;

public class MoveCardUseCaseTest {

  private CardRepository cardRepository;
  private BoardRepository boardRepository;
  private CreateCardUseCase createCardUseCase;
  private CreateColumnUseCase createColumnUseCase;
  private Card card;
  private String fromColumnId;
  private String toColumnId;
  private UUID boardId;
  private DomainEventBus eventBus;
  private CardDTOConverter cardDTOConverter;
  private BoardDTOConverter boardDTOConverter;

  @Before
  public void given_a_board_and_two_columns_and_a_card() {
    this.eventBus = new DomainEventBus();
    this.cardDTOConverter = new CardDTOConverter();
    this.boardDTOConverter = new BoardDTOConverter();
    this.cardRepository = new MemoryCardRepository(new HashMap<UUID, CardDTO>());
    this.boardRepository = new MemoryBoardRepository(new HashMap<UUID, BoardDTO>());
    this.createCardUseCase = new CreateCardUseCase(this.cardRepository, this.eventBus, this.cardDTOConverter);
    this.createColumnUseCase = new CreateColumnUseCase(this.boardRepository, this.eventBus, this.boardDTOConverter);

    Board board = new Board("phd");
    this.boardId = board.getId();
    this.boardRepository.save(this.boardDTOConverter.toDTO(board));
    this.eventBus.register(board);
    this.eventBus.register(new CardCreatedEventHandler(this.cardRepository));

    CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
    CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardUseCaseOutput();
    createCardUseCaseInput.setCardName("test card");
    this.createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);
    UUID cardId = UUID.fromString(createCardUseCaseOutput.getCardId());
    card = this.cardDTOConverter.toEntity(cardRepository.findById(cardId));
    this.eventBus.register(card);

    CreateColumnUseCaseInput createColumnUseCaseInput = new CreateColumnUseCaseInput();
    CreateColumnUseCaseOutput createColumnUseCaseOutput = new CreateColumnUseCaseOutput();
    createColumnUseCaseInput.setBoardId(boardId);
    createColumnUseCaseInput.setTitle("develop column");
    this.fromColumnId = card.getColumnId().toString();
    createColumnUseCaseInput.setBoardId(this.boardId);
    createColumnUseCaseInput.setTitle("test column");
    this.createColumnUseCase.execute(createColumnUseCaseInput, createColumnUseCaseOutput);
    this.toColumnId = createColumnUseCaseOutput.getId();

    SetColumnWIPUseCase setColumnWIPUseCase = new SetColumnWIPUseCase(this.boardRepository, this.eventBus, this.boardDTOConverter);
    SetColumnWIPUseCaseInput setColumnWIPUseCaseInput = new SetColumnWIPUseCaseInput();
    SetColumnWIPUseCaseOutput setColumnWIPUseCaseOutput = new SetColumnWIPUseCaseOutput();

    setColumnWIPUseCaseInput.setBoardId(board.getId());
    setColumnWIPUseCaseInput.setColumnId(UUID.fromString(this.toColumnId));
    setColumnWIPUseCaseInput.setColumnWIP(1);

    setColumnWIPUseCase.execute(setColumnWIPUseCaseInput, setColumnWIPUseCaseOutput);
  }

  @Test
  public void moveCard() {
    MoveCardUseCase moveCardUseCase = new MoveCardUseCase(this.boardRepository, this.eventBus, this.boardDTOConverter);
    MoveCardUseCaseInput moveCardUseCaseInput = new MoveCardUseCaseInput();
    MoveCardUseCaseOutput moveCardUseCaseOutput = new MoveCardUseCaseOutput();

    moveCardUseCaseInput.setBoardId(boardId);
    moveCardUseCaseInput.setCardId(card.getId());
    moveCardUseCaseInput.setFromColumnId(UUID.fromString(fromColumnId));
    moveCardUseCaseInput.setToColumnId(UUID.fromString(toColumnId));

    moveCardUseCase.execute(moveCardUseCaseInput, moveCardUseCaseOutput);

    assertEquals(card.getId().toString(), moveCardUseCaseOutput.getCardId());
    assertEquals(fromColumnId, moveCardUseCaseOutput.getOldColumnId());
    assertEquals(toColumnId, moveCardUseCaseOutput.getNewColumnId());

    Board board = this.boardDTOConverter.toEntity(boardRepository.findById(boardId));
    assertTrue(board.findColumnById(UUID.fromString(toColumnId)).cardExist(card.getId()));
  }

  @Test
  public void testMoveCardEvents() {
    EventLogRepository repo = new MemoryEventLogRepository();
    this.eventBus.register(repo);

    MoveCardUseCase moveCardUseCase = new MoveCardUseCase(this.boardRepository, this.eventBus, this.boardDTOConverter);
    MoveCardUseCaseInput moveCardUseCaseInput = new MoveCardUseCaseInput();
    MoveCardUseCaseOutput moveCardUseCaseOutput = new MoveCardUseCaseOutput();

    moveCardUseCaseInput.setBoardId(boardId);
    moveCardUseCaseInput.setCardId(card.getId());
    moveCardUseCaseInput.setFromColumnId(UUID.fromString(fromColumnId));
    moveCardUseCaseInput.setToColumnId(UUID.fromString(toColumnId));

    assertEquals(0, repo.size());

    moveCardUseCase.execute(moveCardUseCaseInput, moveCardUseCaseOutput);

    assertEquals(2, repo.size());
    List<DomainEvent> eventList = repo.getAll();
    assertEquals("Leaved column event: " + fromColumnId, eventList.get(0).getSourceName());
    assertEquals("Entered column event: " + toColumnId, eventList.get(1).getSourceName());
    assertEquals(toColumnId, this.card.getColumnId().toString());
  }

  @Ignore
  @Test
  public void when_card_moved_to_done_caculate_lead_time() {
    // TODO when_card_moved_to_done_caculate_lead_time
  }

  @Test
  public void the_card_cannot_be_moved_to_the_column_that_has_achieved_its_WIP_limit() {

    CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
    CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardUseCaseOutput();
    createCardUseCaseInput.setCardName("User can move card.");
    createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);
    UUID cardId = UUID.fromString(createCardUseCaseOutput.getCardId());
    card = cardDTOConverter.toEntity(cardRepository.findById(cardId));

    MoveCardUseCase moveCardUseCase = new MoveCardUseCase(this.boardRepository, this.eventBus, this.boardDTOConverter);
    MoveCardUseCaseInput moveCardUseCaseInput = new MoveCardUseCaseInput();
    MoveCardUseCaseOutput moveCardUseCaseOutput = new MoveCardUseCaseOutput();

    moveCardUseCaseInput.setBoardId(boardId);
    moveCardUseCaseInput.setCardId(card.getId());
    moveCardUseCaseInput.setFromColumnId(UUID.fromString(fromColumnId));
    moveCardUseCaseInput.setToColumnId(UUID.fromString(toColumnId));

    moveCardUseCase.execute(moveCardUseCaseInput, moveCardUseCaseOutput);

    MoveCardUseCase moveCardUseCase2 = new MoveCardUseCase(this.boardRepository, this.eventBus, this.boardDTOConverter);
    MoveCardUseCaseInput moveCardUseCaseInput2 = new MoveCardUseCaseInput();
    MoveCardUseCaseOutput moveCardUseCaseOutput2 = new MoveCardUseCaseOutput();

    moveCardUseCaseInput2.setBoardId(boardId);
    moveCardUseCaseInput2.setCardId(card.getId());
    moveCardUseCaseInput2.setFromColumnId(UUID.fromString(fromColumnId));
    moveCardUseCaseInput2.setToColumnId(UUID.fromString(toColumnId));

    try {
      moveCardUseCase2.execute(moveCardUseCaseInput2, moveCardUseCaseOutput2);
      fail("The card cannot be moved to the column that has achieved its WIP limit.");
    } catch (IllegalStateException e) {
      assertEquals("The card cannot be moved to the column that has achieved its WIP limit.", e.getMessage());
    }

  }

}