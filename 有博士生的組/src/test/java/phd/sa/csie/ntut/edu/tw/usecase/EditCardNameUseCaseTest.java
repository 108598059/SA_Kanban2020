package phd.sa.csie.ntut.edu.tw.usecase;

import java.util.HashMap;
import java.util.UUID;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.board.create.CreateBoardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.create.CreateBoardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.board.create.CreateBoardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class EditCardNameUseCaseTest {

  private BoardRepository boardRepository;
  private CardRepository cardRepository;
  private DomainEventBus eventBus;
  private UUID boardId;

  @Before
  public void given_there_is_a_card() {
    setup_respository();
    this.boardId = create_board("Software Architecture", this.boardRepository);
    create_column("Develop", this.boardRepository, this.boardId);
    create_card("User can edit nane.", this.cardRepository, this.eventBus);
  }

  private void setup_respository() {
    this.boardRepository = new MemoryBoardRepository(new HashMap<UUID, Board>());
    this.cardRepository = new MemoryCardRepository(new HashMap<UUID, DTO>());
    this.eventBus = new DomainEventBus();
  }

  private UUID create_board(String boardTitle, BoardRepository repository) {
    CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(repository);
    CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
    CreateBoardUseCaseOutput createBoardUseCaseOutput = new CreateBoardUseCaseOutput();

    createBoardUseCaseInput.setTitle(boardTitle);
    createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutput);
    return UUID.fromString(createBoardUseCaseOutput.getBoardId());
  }

  private void create_column(String columnTitle, BoardRepository repository, UUID boardId) {
    CreateColumnUseCase createColumnUseCase = new CreateColumnUseCase(repository);
    CreateColumnUseCaseInput createColumnUseCaseInput = new CreateColumnUseCaseInput();
    CreateColumnUseCaseOutput createColumnUseCaseOutput = new CreateColumnUseCaseOutput();

    createColumnUseCaseInput.setTitle(columnTitle);
    createColumnUseCaseInput.setBoardId(boardId);

    createColumnUseCase.execute(createColumnUseCaseInput, createColumnUseCaseOutput);
  }

  private void create_card(String cardTitle, CardRepository repository, DomainEventBus eventBus) {
    CreateCardUseCase createCardUseCase = new CreateCardUseCase(repository, eventBus);
    CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
    CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardUseCaseOutput();

    createCardUseCaseInput.setCardName(cardTitle);
    createCardUseCaseInput.setBoardID(this.boardId.toString());

    createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);
  }

  @Ignore
  @Test
  public void editCardName() {
    // TODO
    // EditCardNameUseCase useCase = new EditCardNameUseCase();
    // EditCardNameUseCaseInput input = new EditCardNameUseCaseInput();
    // EditCardNameUseCaseOutput output = new EditCardNameUseCaseOutput();

    // input.setCardName("User can edit name.");

    // userCase.execute(input, output);

    // assertEquals("User can edit name.", output.getEdittedName());
  }

}