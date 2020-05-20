package phd.sa.csie.ntut.edu.tw.usecase.column.create;

import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

public class CreateColumnUseCaseTest {

  private BoardRepository boardRepository;
  private DomainEventBus eventBus;
  private UUID boardID;

  @Before
  public void given_a_board() {
    this.eventBus = new DomainEventBus();
    this.boardRepository = new MemoryBoardRepository();

    Board board = new Board(UUID.randomUUID(), "phd");
    this.boardID = board.getID();
    boardRepository.save(BoardDTOConverter.toDTO(board));
  }

  @Test
  public void createColumn() {
    CreateColumnUseCase createColumnUseCase = new CreateColumnUseCase(this.boardRepository, this.eventBus);
    CreateColumnUseCaseInput createColumnUseCaseInput = new CreateColumnUseCaseInput();
    CreateColumnUseCaseOutput createColumnUseCaseOutput = new CreateColumnUseCaseOutput();

    createColumnUseCaseInput.setTitle("develop");
    createColumnUseCaseInput.setBoardID(boardID);

    createColumnUseCase.execute(createColumnUseCaseInput, createColumnUseCaseOutput);

    assertNotNull(createColumnUseCaseOutput.getID());
  }
}