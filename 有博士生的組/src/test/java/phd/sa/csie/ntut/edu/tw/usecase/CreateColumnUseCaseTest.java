package phd.sa.csie.ntut.edu.tw.usecase;

import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

public class CreateColumnUseCaseTest {

  private BoardRepository boardRepository;
  private DomainEventBus eventBus;
  private UUID boardId;

  @Before
  public void given_a_board() {
    this.eventBus = new DomainEventBus();
    this.boardRepository = new MemoryBoardRepository();

    Board board = new Board("phd");
    this.boardId = board.getId();
    boardRepository.save(BoardDTOConverter.toDTO(board));
  }

  @Test
  public void createColumn() {
    CreateColumnUseCase createColumnUseCase = new CreateColumnUseCase(this.boardRepository, this.eventBus);
    CreateColumnUseCaseInput createColumnUseCaseInput = new CreateColumnUseCaseInput();
    CreateColumnUseCaseOutput createColumnUseCaseOutput = new CreateColumnUseCaseOutput();

    createColumnUseCaseInput.setTitle("develop");
    createColumnUseCaseInput.setBoardId(boardId);

    createColumnUseCase.execute(createColumnUseCaseInput, createColumnUseCaseOutput);

    assertNotNull(createColumnUseCaseOutput.getId());
  }
}