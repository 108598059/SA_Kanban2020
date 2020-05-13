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
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CommitCardUsecase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.EditCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.EditCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.EditCardUseCaseOutput;
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
    this.eventBus = new DomainEventBus();

    this.cardRepository = new MemoryCardRepository();
    this.boardRepository = new MemoryBoardRepository();
    BoardDTOConverter boardDTOConverter = new BoardDTOConverter();
    Board board = new Board("Kanban");
    this.boardRepository.save(boardDTOConverter.toDTO(board));
    this.createCardUseCase = new CreateCardUseCase(this.eventBus);
    this.eventBus.register(new CommitCardUsecase(this.cardRepository, this.boardRepository));

    CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
    CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardUseCaseOutput();
    createCardUseCaseInput.setCardName("Old Name");
    createCardUseCaseInput.setBoardID(board.getId().toString());
    createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);
    UUID cardId = UUID.fromString(createCardUseCaseOutput.getCardId());
    card = CardDTOConverter.toEntity(cardRepository.findById(cardId.toString()));
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