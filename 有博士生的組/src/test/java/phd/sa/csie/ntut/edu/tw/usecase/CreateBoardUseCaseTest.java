package phd.sa.csie.ntut.edu.tw.usecase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.board.create.*;

import java.util.UUID;

public class CreateBoardUseCaseTest {
  BoardRepository boardRepository;

  @Before
  public void setUp() {
    boardRepository = new MemoryBoardRepository();
  }

  @Test
  public void createBoard() {
    CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
    CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
    CreateBoardUseCaseOutput createBoardUseCaseOutput = new CreateBoardUseCaseOutput();

    createBoardUseCaseInput.setTitle("Software Architecture");

    createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutput);

    assertNotNull(createBoardUseCaseOutput.getBoardId());
    assertEquals("Software Architecture", createBoardUseCaseOutput.getBoardTitle());
    Board board = boardRepository.findBoardByUUID(UUID.fromString(createBoardUseCaseOutput.getBoardId()));

    assertEquals(2, board.getNumberOfColumns());
    assertEquals("Backlog", board.get(0).getTitle());
    assertEquals("Archive", board.get(board.getNumberOfColumns() - 1).getTitle());
  }

}