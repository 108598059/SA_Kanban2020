package domain.usecase.workflow.commit;
import domain.adapter.repository.board.MySqlBoardRepository;
import domain.model.DomainEventBus;
import domain.model.aggregate.board.Board;
import domain.usecase.board.BoardTransfer;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.usecase.board.create.CreateBoardUseCaseInput;
import domain.usecase.board.create.CreateBoardUseCaseOutputImpl;
import domain.usecase.board.repository.IBoardRepository;
import domain.usecase.handler.workflow.WorkflowEventHandler;
import org.junit.*;

import static org.junit.Assert.*;
public class CommitWorkflowUseCaseTest {
    private IBoardRepository boardRepository;
    private CreateBoardUseCaseOutputImpl createBoardUseCaseOutputImpl;
    private DomainEventBus eventBus;

    @Before
    public void SetUp() {
        eventBus = new DomainEventBus();
        boardRepository = new MySqlBoardRepository();

        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository, eventBus);
        CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
        createBoardUseCaseOutputImpl = new CreateBoardUseCaseOutputImpl();

        createBoardUseCaseInput.setBoardName("Kanban of KanbanDevelopment");

        createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutputImpl);

        eventBus.register(new WorkflowEventHandler(boardRepository, eventBus));
    }

    @Test
    public void workflow_should_be_committed_in_its_board() {
        CommitWorkflowUseCase commitWorkflowUseCase = new CommitWorkflowUseCase(boardRepository, eventBus);
        CommitWorkflowUseCaseInput input = new CommitWorkflowUseCaseInput();
        CommitWorkflowUseCaseOutput output = new CommitWorkflowUseCaseOutput();

        input.setBoardId(createBoardUseCaseOutputImpl.getBoardId());
        input.setWorkflowId("workflowId");
        commitWorkflowUseCase.execute(input, output);

        Board board = BoardTransfer.BoardDTOToBoard(boardRepository.getBoardById(input.getBoardId()));

        assertEquals(1,board.getWorkflowIds().size());
        assertEquals("workflowId",board.getWorkflowIds().get(0));
    }
}
