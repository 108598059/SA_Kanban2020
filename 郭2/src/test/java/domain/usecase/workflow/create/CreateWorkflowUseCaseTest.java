package domain.usecase.workflow.create;
import domain.adapter.repository.board.MySqlBoardRepository;
import domain.adapter.repository.workflow.MySqlWorkflowRepository;
import domain.model.DomainEventBus;
import domain.model.aggregate.board.Board;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.board.BoardTransfer;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.usecase.board.create.CreateBoardUseCaseInput;
import domain.usecase.board.create.CreateBoardUseCaseOutputImpl;


import domain.usecase.board.repository.IBoardRepository;
import domain.usecase.handler.workflow.WorkflowEventHandler;
import domain.usecase.workflow.WorkflowTransfer;
import domain.usecase.workflow.repository.IWorkflowRepository;
import org.junit.*;

import static org.junit.Assert.*;

public class CreateWorkflowUseCaseTest {
    private IBoardRepository boardRepository;
    private CreateBoardUseCaseOutputImpl createBoardUseCaseOutputImpl;
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    @Before
    public void SetUp(){
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
    public void create_workflow_should_commit_it_to_its_board() {
//        workflowRepository = new InMemoryWorkflowRepository();
        workflowRepository = new MySqlWorkflowRepository();
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository, eventBus);
        CreateWorkflowUseCaseInput input = new CreateWorkflowUseCaseInput();
        CreateWorkflowUseCaseOutput output = new CreateWorkflowUseCaseOutput();

        input.setWorkflowName("KanbanDevelopment");
        input.setBoardId(createBoardUseCaseOutputImpl.getBoardId());

        createWorkflowUseCase.execute(input, output);

        assertNotNull(output.getWorkflowId());
        assertEquals("KanbanDevelopment", output.getWorkflowName());

        Workflow workflow = WorkflowTransfer.WorkflowDTOToWorkflow(workflowRepository.getWorkflowById(output.getWorkflowId()));

        assertEquals(output.getWorkflowId(), workflow.getWorkflowId());
        assertEquals(output.getWorkflowName(), workflow.getWorkflowName());

        Board board = BoardTransfer.BoardDTOToBoard(boardRepository.getBoardById(createBoardUseCaseOutputImpl.getBoardId()));

        assertTrue(board.getWorkflowIds().contains(workflow.getWorkflowId()));
    }
}
