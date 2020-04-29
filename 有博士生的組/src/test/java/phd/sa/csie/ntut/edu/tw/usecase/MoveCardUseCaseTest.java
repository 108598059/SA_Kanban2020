package phd.sa.csie.ntut.edu.tw.usecase;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.repository.*;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.*;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.*;
import phd.sa.csie.ntut.edu.tw.usecase.card.move.*;

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

  @Before
  public void given_a_board_and_two_columns_and_a_card() {
    this.eventBus = new DomainEventBus();
    cardRepository = new MemoryCardRepository();
    boardRepository = new MemoryBoardRepository();
    createCardUseCase = new CreateCardUseCase(cardRepository, this.eventBus);
    createColumnUseCase = new CreateColumnUseCase(boardRepository);

    Board board = new Board("phd");
    boardId = board.getUUID();
    boardRepository.add(board);

    CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
    CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardUseCaseOutput();
    createCardUseCaseInput.setCardName("test card");
    createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);
    card = cardRepository.findCardByUUID(UUID.fromString(createCardUseCaseOutput.getCardId()));

    CreateColumnUseCaseInput createColumnUseCaseInput = new CreateColumnUseCaseInput();
    CreateColumnUseCaseOutput createColumnUseCaseOutput = new CreateColumnUseCaseOutput();
    createColumnUseCaseInput.setBoardId(boardId);
    createColumnUseCaseInput.setTitle("develop column");
    createColumnUseCase.execute(createColumnUseCaseInput, createColumnUseCaseOutput);
    fromColumnId = createColumnUseCaseOutput.getId();
    createColumnUseCaseInput.setBoardId(boardId);
    createColumnUseCaseInput.setTitle("test column");
    createColumnUseCase.execute(createColumnUseCaseInput, createColumnUseCaseOutput);
    toColumnId = createColumnUseCaseOutput.getId();

    board.addCardToColumn(card.getUUID(), UUID.fromString(fromColumnId));
  }

  @Test
  public void moveCard() {
    MoveCardUseCase moveCardUseCase = new MoveCardUseCase(boardRepository);
    MoveCardUseCaseInput moveCardUseCaseInput = new MoveCardUseCaseInput();
    MoveCardUseCaseOutput moveCardUseCaseOutput = new MoveCardUseCaseOutput();

    moveCardUseCaseInput.setBoardId(boardId);
    moveCardUseCaseInput.setCardId(card.getUUID());
    moveCardUseCaseInput.setFromColumnId(UUID.fromString(fromColumnId));
    moveCardUseCaseInput.setToColumnId(UUID.fromString(toColumnId));

    moveCardUseCase.execute(moveCardUseCaseInput, moveCardUseCaseOutput);

    assertEquals(card.getUUID().toString(), moveCardUseCaseOutput.getCardId());
    assertEquals(fromColumnId, moveCardUseCaseOutput.getOldColumnId());
    assertEquals(toColumnId, moveCardUseCaseOutput.getNewColumnId());
    Board board = boardRepository.findBoardByUUID(boardId);
  }

}