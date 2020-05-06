package phd.sa.csie.ntut.edu.tw.usecase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.board.create.CreateBoardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.create.CreateBoardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.board.create.CreateBoardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.column.setwip.SetColumnWIPUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.column.setwip.SetColumnWIPUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.column.setwip.SetColumnWIPUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

public class SetColumnWIPTest {

  private BoardRepository boardRepository;
  private Board board;
  private String columnId;

  @Before
  public void given_there_are_a_column_and_a_board() {
    boardRepository = new MemoryBoardRepository();
    CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
    CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
    CreateBoardUseCaseOutput createBoardUseCaseOutput = new CreateBoardUseCaseOutput();
    createBoardUseCaseInput.setTitle("Software Architecture");
    createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutput);
    board = boardRepository.findBoardByUUID(UUID.fromString(createBoardUseCaseOutput.getBoardId()));

    CreateColumnUseCase createColumnUseCase = new CreateColumnUseCase(boardRepository);
    CreateColumnUseCaseInput createColumnUseCaseInput = new CreateColumnUseCaseInput();
    CreateColumnUseCaseOutput createColumnUseCaseOutput = new CreateColumnUseCaseOutput();
    createColumnUseCaseInput.setBoardId(board.getUUID());
    createColumnUseCaseInput.setTitle("develop");
    createColumnUseCase.execute(createColumnUseCaseInput, createColumnUseCaseOutput);
    columnId = createColumnUseCaseOutput.getId();
  }

  @Test
  public void set_WIP_with_a_positive_number_3() {
    SetColumnWIPUseCase setColumnWIPUseCase = new SetColumnWIPUseCase(boardRepository);
    SetColumnWIPUseCaseInput setColumnWIPUseCaseInput = new SetColumnWIPUseCaseInput();
    SetColumnWIPUseCaseOutput setColumnWIPUseCaseOutput = new SetColumnWIPUseCaseOutput();

    setColumnWIPUseCaseInput.setBoardId(board.getUUID());
    setColumnWIPUseCaseInput.setColumnId(UUID.fromString(columnId));
    setColumnWIPUseCaseInput.setColumnWIP(3);
    setColumnWIPUseCase.execute(setColumnWIPUseCaseInput, setColumnWIPUseCaseOutput);

    assertNotNull(setColumnWIPUseCaseOutput.getColumnId());
    assertEquals(3, setColumnWIPUseCaseOutput.getColumnWIP());
  }

  @Test
  public void the_default_number_of_the_column_WIP_should_be_zero() {
    SetColumnWIPUseCase setColumnWIPUseCase = new SetColumnWIPUseCase(boardRepository);
    SetColumnWIPUseCaseInput setColumnWIPUseCaseInput = new SetColumnWIPUseCaseInput();
    SetColumnWIPUseCaseOutput setColumnWIPUseCaseOutput = new SetColumnWIPUseCaseOutput();

    setColumnWIPUseCaseInput.setBoardId(board.getUUID());
    setColumnWIPUseCaseInput.setColumnId(UUID.fromString(columnId));
    setColumnWIPUseCase.execute(setColumnWIPUseCaseInput, setColumnWIPUseCaseOutput);

    assertNotNull(setColumnWIPUseCaseOutput.getColumnId());
    assertEquals(0, setColumnWIPUseCaseOutput.getColumnWIP());
  }

  @Test
  public void column_WIP_should_be_positive() {
    SetColumnWIPUseCase setColumnWIPUseCase = new SetColumnWIPUseCase(boardRepository);
    SetColumnWIPUseCaseInput setColumnWIPUseCaseInput = new SetColumnWIPUseCaseInput();
    SetColumnWIPUseCaseOutput setColumnWIPUseCaseOutput = new SetColumnWIPUseCaseOutput();

    setColumnWIPUseCaseInput.setBoardId(board.getUUID());
    setColumnWIPUseCaseInput.setColumnId(UUID.fromString(columnId));
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