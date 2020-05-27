package phd.sa.csie.ntut.edu.tw.usecase.column.create;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

public class CreateColumnUseCaseTest {

  private BoardRepository boardRepository;
  private UUID boardID;

  @Before
  public void given_a_board() {
    this.boardRepository = new MemoryBoardRepository();

    Board board = new Board(UUID.randomUUID(), "phd");
    this.boardID = board.getID();
    this.boardRepository.save(BoardDTOConverter.toDTO(board));
  }

  @Test
  public void create_column_should_be_added_to_board() {
    CreateColumnUseCase createColumnUseCase = new CreateColumnUseCase(this.boardRepository);
    CreateColumnUseCaseInput createColumnUseCaseInput = new CreateColumnUseCaseInput();
    CreateColumnUseCaseOutput createColumnUseCaseOutput = new CreateColumnUseCaseOutput();

    createColumnUseCaseInput.setTitle("develop");
    createColumnUseCaseInput.setBoardID(this.boardID.toString());

    createColumnUseCase.execute(createColumnUseCaseInput, createColumnUseCaseOutput);

    assertNotNull(createColumnUseCaseOutput.getID());

    BoardDTO boardDTO = this.boardRepository.findByID(this.boardID.toString());
    assertEquals("develop", boardDTO.getColumnDTOs().get(1).getTitle());
  }
}