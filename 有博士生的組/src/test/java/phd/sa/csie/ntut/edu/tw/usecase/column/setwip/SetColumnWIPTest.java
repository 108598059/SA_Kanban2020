package phd.sa.csie.ntut.edu.tw.usecase.column.setwip;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.board.create.CreateBoardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.create.CreateBoardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.board.create.CreateBoardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

public class SetColumnWIPTest {

  private BoardRepository boardRepository;
  private DomainEventBus eventBus;
  private Board board;
  private String columnID;

  @Before
  public void given_there_are_a_column_and_a_board() {
    this.eventBus = new DomainEventBus();
    boardRepository = new MemoryBoardRepository();

    CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(this.boardRepository);
    CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
    CreateBoardUseCaseOutput createBoardUseCaseOutput = new CreateBoardUseCaseOutput();
    createBoardUseCaseInput.setBoardName("Software Architecture");
    createBoardUseCaseInput.setWorkspaceID(UUID.randomUUID().toString());
    createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutput);
    UUID boardID = UUID.fromString(createBoardUseCaseOutput.getBoardID());
    board = BoardDTOConverter.toEntity(this.boardRepository.findByID(boardID.toString()));

    CreateColumnUseCase createColumnUseCase = new CreateColumnUseCase(this.boardRepository);
    CreateColumnUseCaseInput createColumnUseCaseInput = new CreateColumnUseCaseInput();
    CreateColumnUseCaseOutput createColumnUseCaseOutput = new CreateColumnUseCaseOutput();
    createColumnUseCaseInput.setBoardID(board.getID().toString());
    createColumnUseCaseInput.setTitle("develop");
    createColumnUseCase.execute(createColumnUseCaseInput, createColumnUseCaseOutput);
    columnID = createColumnUseCaseOutput.getID();
  }

  @Test
  public void set_WIP_with_a_positive_number_3() {
    SetColumnWIPUseCase setColumnWIPUseCase = new SetColumnWIPUseCase(this.boardRepository, this.eventBus);
    SetColumnWIPUseCaseInput setColumnWIPUseCaseInput = new SetColumnWIPUseCaseInput();
    SetColumnWIPUseCaseOutput setColumnWIPUseCaseOutput = new SetColumnWIPUseCaseOutput();

    setColumnWIPUseCaseInput.setBoardID(board.getID());
    setColumnWIPUseCaseInput.setColumnID(UUID.fromString(columnID));
    setColumnWIPUseCaseInput.setColumnWIP(3);

    setColumnWIPUseCase.execute(setColumnWIPUseCaseInput, setColumnWIPUseCaseOutput);

    assertNotNull(setColumnWIPUseCaseOutput.getColumnID());
    assertEquals(3, setColumnWIPUseCaseOutput.getColumnWIP());
  }

  @Test
  public void the_default_number_of_the_column_WIP_should_be_zero() {
    SetColumnWIPUseCase setColumnWIPUseCase = new SetColumnWIPUseCase(this.boardRepository, this.eventBus);
    SetColumnWIPUseCaseInput setColumnWIPUseCaseInput = new SetColumnWIPUseCaseInput();
    SetColumnWIPUseCaseOutput setColumnWIPUseCaseOutput = new SetColumnWIPUseCaseOutput();

    setColumnWIPUseCaseInput.setBoardID(board.getID());
    setColumnWIPUseCaseInput.setColumnID(UUID.fromString(columnID));

    setColumnWIPUseCase.execute(setColumnWIPUseCaseInput, setColumnWIPUseCaseOutput);

    assertNotNull(setColumnWIPUseCaseOutput.getColumnID());
    assertEquals(0, setColumnWIPUseCaseOutput.getColumnWIP());
  }

  @Test
  public void column_WIP_should_be_positive() {
    SetColumnWIPUseCase setColumnWIPUseCase = new SetColumnWIPUseCase(this.boardRepository, this.eventBus);
    SetColumnWIPUseCaseInput setColumnWIPUseCaseInput = new SetColumnWIPUseCaseInput();
    SetColumnWIPUseCaseOutput setColumnWIPUseCaseOutput = new SetColumnWIPUseCaseOutput();

    setColumnWIPUseCaseInput.setBoardID(board.getID());
    setColumnWIPUseCaseInput.setColumnID(UUID.fromString(columnID));
    setColumnWIPUseCaseInput.setColumnWIP(-1);

    try {
      setColumnWIPUseCase.execute(setColumnWIPUseCaseInput, setColumnWIPUseCaseOutput);
      fail("Column WIP should be positive.");
    } catch (IllegalArgumentException e) {
      assertEquals("Column WIP should be positive.", e.getMessage());
    } catch (Exception e) {
      fail("Should throw an \"IllegalArgumentException\" with message: \"Column WIP should be positive.\".");
    }
  }

}