package phd.sa.csie.ntut.edu.tw.usecase;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.board.column.create.*;
import phd.sa.csie.ntut.edu.tw.usecase.board.column.setwip.*;
import phd.sa.csie.ntut.edu.tw.usecase.board.create.*;

public class SetColumnWIPTest {

  private BoardRepository boardRepository;
  private Board board;
  private String columnTitle;

  @Before
  public void init() {
    boardRepository = new BoardRepository();
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
    columnTitle = createColumnUseCaseOutput.getTitle();
  }

  @Test
  public void setWIP() {
    SetColumnWIPUseCase setColumnWIPUseCase = new SetColumnWIPUseCase(boardRepository);
    SetColumnWIPUseCaseInput setColumnWIPUseCaseInput = new SetColumnWIPUseCaseInput();
    SetColumnWIPUseCaseOutput setColumnWIPUseCaseOutput = new SetColumnWIPUseCaseOutput();
    setColumnWIPUseCaseInput.setBoardId(board.getUUID());
    setColumnWIPUseCaseInput.setColumnTitle(columnTitle);
    setColumnWIPUseCaseInput.setColumnWIP(3);
    setColumnWIPUseCase.execute(setColumnWIPUseCaseInput, setColumnWIPUseCaseOutput);
    assertEquals("develop", setColumnWIPUseCaseOutput.getColumnTitle());
    assertEquals(3, setColumnWIPUseCaseOutput.getColumnWIP());
  }

  @Rule
  public ExpectedException exception = ExpectedException.none();
  @Test
  public void setWIPWithNegativeValue() {
    exception.expect(IllegalArgumentException.class);
    SetColumnWIPUseCase setColumnWIPUseCase = new SetColumnWIPUseCase(boardRepository);
    SetColumnWIPUseCaseInput setColumnWIPUseCaseInput = new SetColumnWIPUseCaseInput();
    SetColumnWIPUseCaseOutput setColumnWIPUseCaseOutput = new SetColumnWIPUseCaseOutput();
    setColumnWIPUseCaseInput.setBoardId(board.getUUID());
    setColumnWIPUseCaseInput.setColumnTitle(columnTitle);
    setColumnWIPUseCaseInput.setColumnWIP(-1);
    setColumnWIPUseCase.execute(setColumnWIPUseCaseInput, setColumnWIPUseCaseOutput);
  }

}