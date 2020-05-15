package phd.sa.csie.ntut.edu.tw.usecase.card.edit;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.DomainEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class EditCardUseCaseTest {

  private Card card;
  private CardRepository cardRepository;
  private BoardRepository boardRepository;
  private CreateCardUseCase createCardUseCase;
  private DomainEventBus eventBus;

  @Before
  public void given_a_card() {
    eventBus = new DomainEventBus();
    cardRepository = new MemoryCardRepository();
    boardRepository = new MemoryBoardRepository();

    Board board = new Board("Kanban");
    boardRepository.save(BoardDTOConverter.toDTO(board));

    eventBus.register(new DomainEventHandler(cardRepository, boardRepository));
    createCardUseCase = new CreateCardUseCase(cardRepository, eventBus);
    CreateCardUseCaseInput input = new CreateCardUseCaseInput();
    CreateCardUseCaseOutput output = new CreateCardUseCaseOutput();
    input.setCardName("Old Name");
    input.setBoardId(board.getId());
    input.setColumnId(board.get(0).getId());
    createCardUseCase.execute(input, output);

    card = CardDTOConverter.toEntity(this.cardRepository.findById(output.getCardId()));
  }

  @Test
  public void editCard() {
    EditCardUseCase editCardUseCase = new EditCardUseCase(this.cardRepository);
    EditCardUseCaseInput input = new EditCardUseCaseInput();
    EditCardUseCaseOutput output = new EditCardUseCaseOutput();

    input.setCardId(this.card.getId());
    input.setCardName("New Name");

    editCardUseCase.execute(input, output);

    assertEquals(this.card.getId().toString(), output.getCardId());
    assertEquals("New Name", output.getCardName());
  }

}