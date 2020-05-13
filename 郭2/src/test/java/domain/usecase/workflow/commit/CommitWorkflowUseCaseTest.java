package domain.usecase.workflow.commit;
import domain.adapter.repository.board.MySqlBoardRepository;
import domain.model.aggregate.DomainEventBus;
import domain.model.aggregate.board.Board;
import domain.usecase.board.BoardDTO;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.usecase.board.create.CreateBoardUseCaseInput;
import domain.usecase.board.create.CreateBoardUseCaseOutput;
import domain.usecase.board.repository.IBoardRepository;
import domain.usecase.workflow.WorkflowEventHandler;
import org.junit.*;

import static org.junit.Assert.*;
public class CommitWorkflowUseCaseTest {
    private IBoardRepository boardRepository;
    private CreateBoardUseCaseOutput createBoardUseCaseOutput;
    private DomainEventBus eventBus;

    @Before
    public void SetUp() {
        eventBus = new DomainEventBus();
        boardRepository = new MySqlBoardRepository();

        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
        CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
        createBoardUseCaseOutput = new CreateBoardUseCaseOutput();

        createBoardUseCaseInput.setBoardName("Kanban of KanbanDevelopment");

        createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutput);

        eventBus.register(new WorkflowEventHandler(boardRepository));
    }

    @Test
    public void Workflow_should_be_committed_in_its_board() {
        CommitWorkflowUseCase commitWorkflowUseCase = new CommitWorkflowUseCase(boardRepository);
        CommitWorkflowUseCaseInput input = new CommitWorkflowUseCaseInput();
        CommitWorkflowUseCaseOutput output = new CommitWorkflowUseCaseOutput();

        input.setBoardId(createBoardUseCaseOutput.getBoardId());
        input.setWorkflowId("workflowId");
        commitWorkflowUseCase.execute(input, output);

        Board board = BoardDTO.BoardEntityToBoard(boardRepository.getBoardById(input.getBoardId()));

        assertEquals(1,board.getWorkflowIds().size());
        assertEquals("workflowId",board.getWorkflowIds().get(0));
    }
}
