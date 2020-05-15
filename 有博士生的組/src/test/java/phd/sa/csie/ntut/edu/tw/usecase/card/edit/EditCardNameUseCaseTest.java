package phd.sa.csie.ntut.edu.tw.usecase.card.edit;

import java.util.UUID;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
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

public class EditCardNameUseCaseTest {

  private BoardRepository boardRepository;
  private DomainEventBus eventBus;
  private UUID boardId;

  @Before
  public void given_there_is_a_card() {
    this.setup_context_for_use_case();
    this.boardId = create_board("Software Architecture", this.boardRepository, this.eventBus);
    this.create_column("Develop", this.boardId, this.boardRepository, this.eventBus);
    this.create_card("User can edit nane.");
  }

  private void setup_context_for_use_case() {
    this.boardRepository = new MemoryBoardRepository();
    this.eventBus = new DomainEventBus();
  }

  private UUID create_board(String boardTitle, BoardRepository repository, DomainEventBus eventBus) {
    CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(repository, eventBus);
    CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
    CreateBoardUseCaseOutput createBoardUseCaseOutput = new CreateBoardUseCaseOutput();

    createBoardUseCaseInput.setBoardName(boardTitle);
    createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutput);
    return UUID.fromString(createBoardUseCaseOutput.getBoardId());
  }

  private void create_column(String columnTitle, UUID boardId, BoardRepository repository, DomainEventBus eventBus) {
    CreateColumnUseCase createColumnUseCase = new CreateColumnUseCase(repository, eventBus);
    CreateColumnUseCaseInput createColumnUseCaseInput = new CreateColumnUseCaseInput();
    CreateColumnUseCaseOutput createColumnUseCaseOutput = new CreateColumnUseCaseOutput();

    createColumnUseCaseInput.setTitle(columnTitle);
    createColumnUseCaseInput.setBoardId(boardId);

    createColumnUseCase.execute(createColumnUseCaseInput, createColumnUseCaseOutput);
  }

  private void create_card(String cardTitle) {
    CreateCardUseCase createCardUseCase = new CreateCardUseCase(this.eventBus);
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