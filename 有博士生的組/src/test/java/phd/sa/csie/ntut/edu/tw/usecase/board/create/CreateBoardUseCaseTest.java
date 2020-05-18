package phd.sa.csie.ntut.edu.tw.usecase.board.create;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;

public class CreateBoardUseCaseTest {
  BoardRepository boardRepository;
  DomainEventBus eventBus;

  @Before
  public void setUp() {
    this.eventBus = new DomainEventBus();
    this.boardRepository = new MemoryBoardRepository();
  }

  @Test
  public void board_created() {
    CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(this.boardRepository);
    CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
    CreateBoardUseCaseOutput createBoardUseCaseOutput = new CreateBoardUseCaseOutput();

    createBoardUseCaseInput.setBoardName("Software Architecture");

    createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutput);

    assertNotNull(createBoardUseCaseOutput.getBoardId());
    assertEquals("Software Architecture", createBoardUseCaseOutput.getBoardName());
  }

  @Test
  public void creating_a_new_board_should_generate_backlog_column_and_archive_column() {
    CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(this.boardRepository);
    CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
    CreateBoardUseCaseOutput createBoardUseCaseOutput = new CreateBoardUseCaseOutput();

    createBoardUseCaseInput.setBoardName("Software Architecture");
    createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutput);

    Board board = BoardDTOConverter.toEntity(this.boardRepository.findById(createBoardUseCaseOutput.getBoardId()));

    assertEquals(2, board.getColumnNumber());
    assertEquals("Software Architecture", createBoardUseCaseOutput.getBoardName());
    assertEquals("Backlog", board.get(0).getTitle());
    assertEquals("Archive", board.get(board.getColumnNumber() - 1).getTitle());
  }
}