package phd.sa.csie.ntut.edu.tw.usecase.board.create;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.adapter.presenter.board.create.CreateBoardPresenter;
import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.aggregate.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.board.BoardCreatedEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.board.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;

import java.util.UUID;

public class CreateBoardUseCaseTest {
    BoardRepository boardRepository;
    DomainEventBus eventBus;

    @Before
    public void setUp() {
        this.eventBus = new DomainEventBus();
        this.boardRepository = new MemoryBoardRepository();
        BoardCreatedEventHandler boardCreatedEventHandler = new BoardCreatedEventHandler(this.eventBus, this.boardRepository);
        this.eventBus.register(boardCreatedEventHandler);
    }

    @Test
    public void board_created() {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(this.eventBus, this.boardRepository);
        CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
        CreateBoardUseCaseOutput createBoardUseCaseOutput = new CreateBoardPresenter();

        createBoardUseCaseInput.setBoardName("Software Architecture");
        createBoardUseCaseInput.setWorkspaceID(UUID.randomUUID().toString());

        createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutput);

        assertNotNull(createBoardUseCaseOutput.getBoardID());
        assertEquals("Software Architecture", createBoardUseCaseOutput.getBoardName());
    }

    @Test
    public void creating_a_new_board_should_generate_backlog_column_and_archive_column() {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(this.eventBus, this.boardRepository);
        CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
        CreateBoardUseCaseOutput createBoardUseCaseOutput = new CreateBoardPresenter();

        UUID workspaceID = UUID.randomUUID();
        createBoardUseCaseInput.setBoardName("Software Architecture");
        createBoardUseCaseInput.setWorkspaceID(workspaceID.toString());
        createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutput);

        Board board = BoardDTOConverter.toEntity(this.boardRepository.findByID(createBoardUseCaseOutput.getBoardID()));

        assertEquals(2, board.getColumnNumber());
        assertEquals("Software Architecture", board.getName());
        assertEquals("Backlog", board.get(0).getTitle());
        assertEquals("Archive", board.get(board.getColumnNumber() - 1).getTitle());
        assertEquals(workspaceID, board.getWorkspaceID());
    }
}