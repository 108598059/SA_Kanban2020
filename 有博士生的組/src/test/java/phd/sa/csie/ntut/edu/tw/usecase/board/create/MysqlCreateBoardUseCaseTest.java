package phd.sa.csie.ntut.edu.tw.usecase.board.create;

import org.junit.Before;
import org.junit.Test;
import phd.sa.csie.ntut.edu.tw.adapter.repository.mysql.MysqlBoardRepository;
import phd.sa.csie.ntut.edu.tw.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class MysqlCreateBoardUseCaseTest {
    private BoardRepository boardRepository;
    private DomainEventBus eventBus;

    @Before
    public void setUp() {
        this.boardRepository = new MysqlBoardRepository();
        this.eventBus = new DomainEventBus();
    }

    @Test
    public void create_board() {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(this.eventBus, this.boardRepository);
        CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
        CreateBoardUseCaseOutput createBoardUseCaseOutput = new CreateBoardUseCaseOutput();

        UUID workspaceID = UUID.randomUUID();

        createBoardUseCaseInput.setBoardName("Software Architecture");
        createBoardUseCaseInput.setWorkspaceID(workspaceID.toString());

        createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutput);

        Board board = BoardDTOConverter.toEntity(this.boardRepository.findByID(createBoardUseCaseOutput.getBoardID()));

        assertEquals(2, board.getColumnNumber());
        assertEquals("Software Architecture", createBoardUseCaseOutput.getBoardName());
        assertEquals("Backlog", board.get(0).getTitle());
        assertEquals("Archive", board.get(board.getColumnNumber() - 1).getTitle());
        assertEquals(workspaceID, board.getWorkspaceID());
    }
}
