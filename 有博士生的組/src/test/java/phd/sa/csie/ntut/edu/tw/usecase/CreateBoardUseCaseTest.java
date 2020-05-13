package phd.sa.csie.ntut.edu.tw.usecase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.board.create.*;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;

import java.util.HashMap;
import java.util.UUID;

public class CreateBoardUseCaseTest {
  BoardRepository boardRepository;
  BoardDTOConverter boardDTOConverter;
  DomainEventBus eventBus;

  @Before
  public void setUp() {
    this.eventBus = new DomainEventBus();
    this.boardDTOConverter = new BoardDTOConverter();
    this.boardRepository = new MemoryBoardRepository();
  }

  @Test
  public void board_created() {
    CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(this.boardRepository, this.eventBus, this.boardDTOConverter);
    CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
    CreateBoardUseCaseOutput createBoardUseCaseOutput = new CreateBoardUseCaseOutput();

    createBoardUseCaseInput.setTitle("Software Architecture");
    createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutput);

    assertNotNull(createBoardUseCaseOutput.getBoardId());
    assertEquals("Software Architecture", createBoardUseCaseOutput.getBoardTitle());
  }

  @Test
  public void creating_a_new_board_should_generate_backlog_column_and_archive_column() {
    CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(this.boardRepository, this.eventBus, this.boardDTOConverter);
    CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
    CreateBoardUseCaseOutput createBoardUseCaseOutput = new CreateBoardUseCaseOutput();

    createBoardUseCaseInput.setTitle("Software Architecture");
    createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutput);

    UUID boardId = UUID.fromString(createBoardUseCaseOutput.getBoardId());
    Board board = this.boardDTOConverter.toEntity(this.boardRepository.findById(boardId.toString()));

    assertEquals(2, board.getColumnNumber());
    assertEquals("Backlog", board.get(0).getTitle());
    assertEquals("Archive", board.get(board.getColumnNumber() - 1).getTitle());
  }

}