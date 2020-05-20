package phd.sa.csie.ntut.edu.tw.usecase.board.move;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.MemoryEventLogRepository;
import phd.sa.csie.ntut.edu.tw.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.board.commit.card.CommitCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.column.setwip.SetColumnWIPUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.column.setwip.SetColumnWIPUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.column.setwip.SetColumnWIPUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.CardEnterColumnEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.DomainEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.EventSourcingHandler;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.dto.DomainEventDTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.EventLogRepository;

public class MoveCardUseCaseTest {

  private CardRepository cardRepository;
  private BoardRepository boardRepository;
  private CreateCardUseCase createCardUseCase;
  private CreateColumnUseCase createColumnUseCase;
  private Card card;
  private Card card2;
  private String fromColumnID;
  private String toColumnID;
  private UUID boardID;
  private DomainEventBus eventBus;

  @Before
  public void given_a_board_and_two_columns_and_a_card() {
    this.eventBus = new DomainEventBus();
    this.cardRepository = new MemoryCardRepository();
    this.boardRepository = new MemoryBoardRepository();
    this.createCardUseCase = new CreateCardUseCase(this.eventBus, this.cardRepository, this.boardRepository);
    this.createColumnUseCase = new CreateColumnUseCase(this.boardRepository, this.eventBus);

    Board board = new Board(UUID.randomUUID(), "phd");
    this.boardID = board.getID();
    this.boardRepository.save(BoardDTOConverter.toDTO(board));
    this.eventBus.register(new CommitCardUseCase(this.cardRepository, this.boardRepository));

    CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
    CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardUseCaseOutput();
    createCardUseCaseInput.setCardName("test card");
    createCardUseCaseInput.setBoardID(this.boardID.toString());
    this.createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);
    this.card = CardDTOConverter.toEntity(this.cardRepository.findByID(createCardUseCaseOutput.getCardID()));

    createCardUseCaseInput.setCardName("test card2");
    this.createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);
    this.card2 = CardDTOConverter.toEntity(this.cardRepository.findByID(createCardUseCaseOutput.getCardID()));

    DomainEventHandler cardEnterColumnEventHandler = new CardEnterColumnEventHandler(this.cardRepository);
    this.eventBus.register(cardEnterColumnEventHandler);

    CreateColumnUseCaseInput createColumnUseCaseInput = new CreateColumnUseCaseInput();
    CreateColumnUseCaseOutput createColumnUseCaseOutput = new CreateColumnUseCaseOutput();
    createColumnUseCaseInput.setBoardID(this.boardID);
    createColumnUseCaseInput.setTitle("develop column");
    this.fromColumnID = this.card.getColumnID().toString();
    createColumnUseCaseInput.setBoardID(this.boardID);
    createColumnUseCaseInput.setTitle("test column");
    this.createColumnUseCase.execute(createColumnUseCaseInput, createColumnUseCaseOutput);
    this.toColumnID = createColumnUseCaseOutput.getID();

    SetColumnWIPUseCase setColumnWIPUseCase = new SetColumnWIPUseCase(this.boardRepository, this.eventBus);
    SetColumnWIPUseCaseInput setColumnWIPUseCaseInput = new SetColumnWIPUseCaseInput();
    SetColumnWIPUseCaseOutput setColumnWIPUseCaseOutput = new SetColumnWIPUseCaseOutput();

    setColumnWIPUseCaseInput.setBoardID(board.getID());
    setColumnWIPUseCaseInput.setColumnID(UUID.fromString(this.toColumnID));
    setColumnWIPUseCaseInput.setColumnWIP(1);

    setColumnWIPUseCase.execute(setColumnWIPUseCaseInput, setColumnWIPUseCaseOutput);
  }

  @Test
  public void moveCard() {
    MoveCardUseCase moveCardUseCase = new MoveCardUseCase(this.boardRepository, this.eventBus);
    MoveCardUseCaseInput moveCardUseCaseInput = new MoveCardUseCaseInput();
    MoveCardUseCaseOutput moveCardUseCaseOutput = new MoveCardUseCaseOutput();

    moveCardUseCaseInput.setBoardID(this.boardID);
    moveCardUseCaseInput.setCardID(this.card.getID());
    moveCardUseCaseInput.setFromColumnID(UUID.fromString(this.fromColumnID));
    moveCardUseCaseInput.setToColumnID(UUID.fromString(this.toColumnID));

    moveCardUseCase.execute(moveCardUseCaseInput, moveCardUseCaseOutput);

    assertEquals(this.card.getID().toString(), moveCardUseCaseOutput.getCardID());
    assertEquals(this.fromColumnID, moveCardUseCaseOutput.getOldColumnID());
    assertEquals(this.toColumnID, moveCardUseCaseOutput.getNewColumnID());

    Board board = BoardDTOConverter.toEntity(this.boardRepository.findByID(this.boardID.toString()));
    assertTrue(board.findColumnByID(UUID.fromString(this.toColumnID)).cardExist(this.card.getID()));
  }

  @Test
  public void move_card_should_post_events_and_update_column_of_card() {
    EventLogRepository repo = new MemoryEventLogRepository();
    DomainEventHandler eventSourcingHandler = new EventSourcingHandler(repo);
    this.eventBus.register(eventSourcingHandler);

    MoveCardUseCase moveCardUseCase = new MoveCardUseCase(this.boardRepository, this.eventBus);
    MoveCardUseCaseInput moveCardUseCaseInput = new MoveCardUseCaseInput();
    MoveCardUseCaseOutput moveCardUseCaseOutput = new MoveCardUseCaseOutput();

    moveCardUseCaseInput.setBoardID(this.boardID);
    moveCardUseCaseInput.setCardID(this.card.getID());
    moveCardUseCaseInput.setFromColumnID(UUID.fromString(this.fromColumnID));
    moveCardUseCaseInput.setToColumnID(UUID.fromString(this.toColumnID));

    assertEquals(0, repo.size());

    moveCardUseCase.execute(moveCardUseCaseInput, moveCardUseCaseOutput);

    assertEquals(2, repo.size());
    List<DomainEventDTO> eventList = repo.getAll();
    Card resultCard = CardDTOConverter.toEntity(this.cardRepository.findByID(this.card.getID().toString()));
    assertEquals("Leaved column event: " + this.fromColumnID, eventList.get(0).getSourceName());
    assertEquals("Entered column event: " + this.toColumnID, eventList.get(1).getSourceName());
    assertEquals(this.toColumnID, resultCard.getColumnID().toString());
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
    createCardUseCaseInput.setBoardID(this.boardID.toString());
    this.createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);
    this.card = CardDTOConverter.toEntity(this.cardRepository.findByID(createCardUseCaseOutput.getCardID()));

    MoveCardUseCase moveCardUseCase = new MoveCardUseCase(this.boardRepository, this.eventBus);
    MoveCardUseCaseInput moveCardUseCaseInput = new MoveCardUseCaseInput();
    MoveCardUseCaseOutput moveCardUseCaseOutput = new MoveCardUseCaseOutput();

    moveCardUseCaseInput.setBoardID(this.boardID);
    moveCardUseCaseInput.setCardID(this.card.getID());
    moveCardUseCaseInput.setFromColumnID(UUID.fromString(this.fromColumnID));
    moveCardUseCaseInput.setToColumnID(UUID.fromString(this.toColumnID));

    moveCardUseCase.execute(moveCardUseCaseInput, moveCardUseCaseOutput);

    moveCardUseCaseInput.setCardID(this.card2.getID());

    try {
      moveCardUseCase.execute(moveCardUseCaseInput, moveCardUseCaseOutput);
      fail("The card cannot be moved to the column that has achieved its WIP limit.");
    } catch (IllegalStateException e) {
      assertEquals("The card cannot be moved to the column that has achieved its WIP limit.", e.getMessage());
    }
  }
}