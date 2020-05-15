package phd.sa.csie.ntut.edu.tw.usecase.card.create;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import com.google.common.eventbus.Subscribe;

import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.domain.model.card.event.CardCreatedEvent;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CommitCardUsecase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class CreateCardUseCaseTest {

  private CardRepository cardRepository;
  private BoardRepository boardRepository;
  private DomainEventBus eventBus;
  private CommitCardUsecase commitCardUsecase;
  private Board board;
  private CreateCardUseCase createCardUseCase;
  private CreateCardUseCaseInput input;
  private CreateCardUseCaseOutput output;

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
  public void given_a_board_and_a_column() {
    cardRepository = new MemoryCardRepository();
    boardRepository = new MemoryBoardRepository();
    eventBus = new DomainEventBus();

    createCardUseCase = new CreateCardUseCase(cardRepository, eventBus);
    input = new CreateCardUseCaseInput();
    output = new CreateCardUseCaseOutput();

    board = new Board("CA Practicing");
    boardRepository.save(BoardDTOConverter.toDTO(board));
  }

  @Test
  public void create_card() {
    input.setCardName("Event storming");
    input.setBoardId(board.getId());
    input.setColumnId(board.get(0).getId());

    createCardUseCase.execute(input, output);

    assertEquals("Event storming", output.getCardName());
    assertNotEquals("", output.getCardId());
    assertNotNull("New card should have an uuid.", output.getCardId());
  }

  @Test
  public void creating_a_new_card_should_commit_the_card_to_the_backlog_column() {
    commitCardUsecase = new CommitCardUsecase(cardRepository, boardRepository);
    eventBus.register(commitCardUsecase);

    input.setCardName("Create Card");
    input.setBoardId(board.getId());
    input.setColumnId(board.get(0).getId());

    createCardUseCase.execute(input, output);

    String cardId = output.getCardId();
    Card card = CardDTOConverter.toEntity(cardRepository.findById(cardId));
    Board updatedBoard = BoardDTOConverter.toEntity(boardRepository.findById(board.getId().toString()));
    assertEquals(updatedBoard.get(0).getCardIds().get(0), card.getId());
  }

  @Test
  public void committed_card_change_should_be_save_to_the_board_repository() {
    eventBus.register(new CommitCardUsecase(cardRepository, boardRepository));

    input.setCardName("Create Card");
    input.setBoardId(board.getId());
    input.setColumnId(board.get(0).getId());

    createCardUseCase.execute(input, output);

    String cardId = output.getCardId();
    Card card = CardDTOConverter.toEntity(cardRepository.findById(cardId));
    Board updatedBoard = BoardDTOConverter.toEntity(boardRepository.findById(board.getId().toString()));
    assertEquals(updatedBoard.get(0).getId().toString(), card.getColumnId().toString());
  }

  @Test
  public void create_card_event_should_be_issued_when_a_card_being_created() {
    input.setCardName("Create Card");
    input.setBoardId(board.getId());
    input.setColumnId(board.get(0).getId());

    MockCardCreatedEventListener mockListener = new MockCardCreatedEventListener();
    eventBus.register(mockListener);

    createCardUseCase.execute(input, output);

    assertEquals(1, mockListener.getEventCount());
  }
}