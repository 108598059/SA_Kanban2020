package domain.usecase.workflow.create;
import domain.adapter.repository.board.MySqlBoardRepository;
import domain.adapter.repository.workflow.MySqlWorkflowRepository;
import domain.model.aggregate.DomainEventBus;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.usecase.board.create.CreateBoardUseCaseInput;
import domain.usecase.board.create.CreateBoardUseCaseOutput;


import domain.usecase.board.repository.IBoardRepository;
import domain.usecase.workflow.WorkflowEventHandler;
import domain.usecase.workflow.repository.IWorkflowRepository;
import org.junit.*;

import static org.junit.Assert.*;

public class CreateWorkflowUseCaseTest {
    private IBoardRepository boardRepository;
    private CreateBoardUseCaseOutput createBoardUseCaseOutput;
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    @Before
    public void SetUp(){
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
    public void createWorkflowTest(){
//        workflowRepository = new InMemoryWorkflowRepository();
        workflowRepository = new MySqlWorkflowRepository();
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository, boardRepository, eventBus);
        CreateWorkflowUseCaseInput input = new CreateWorkflowUseCaseInput();
        CreateWorkflowUseCaseOutput output = new CreateWorkflowUseCaseOutput();

        input.setWorkflowName("KanbanDevelopment");
        input.setBoardId(createBoardUseCaseOutput.getBoardId());

        createWorkflowUseCase.execute(input, output);

        assertNotNull(output.getWorkflowId());
        assertEquals("KanbanDevelopment", output.getWorkflowName());

        Workflow workflow = workflowRepository.getWorkflowById(output.getWorkflowId());

        assertEquals(output.getWorkflowId(), workflow.getWorkflowId());
        assertEquals(output.getWorkflowName(), workflow.getWorkflowName());
    }
}
