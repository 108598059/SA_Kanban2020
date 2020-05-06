package phd.sa.csie.ntut.edu.tw.usecase;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

public class CreateColumnUseCaseTest {

  private BoardRepository boardRepository;
  private UUID boardUUID;

  @Before
  public void given_a_board() {
    boardRepository = new MemoryBoardRepository(new HashMap<UUID, Board>());
    Board board = new Board("phd");
    boardUUID = board.getId();
    boardRepository.add(board);
  }

  @Test
  public void createColumn() {
    CreateColumnUseCase createColumnUseCase = new CreateColumnUseCase(boardRepository);
    CreateColumnUseCaseInput createColumnUseCaseInput = new CreateColumnUseCaseInput();
    CreateColumnUseCaseOutput createColumnUseCaseOutput = new CreateColumnUseCaseOutput();

    createColumnUseCaseInput.setTitle("develop");
    createColumnUseCaseInput.setBoardId(boardUUID);

    createColumnUseCase.execute(createColumnUseCaseInput, createColumnUseCaseOutput);

    assertNotNull(createColumnUseCaseOutput.getId());
    // TODO assert column title
  }

}