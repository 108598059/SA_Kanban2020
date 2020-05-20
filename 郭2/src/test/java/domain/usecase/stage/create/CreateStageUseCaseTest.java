package domain.usecase.stage.create;

import domain.adapter.repository.board.MySqlBoardRepository;
import domain.adapter.repository.workflow.MySqlWorkflowRepository;
import domain.model.aggregate.DomainEventBus;
import domain.model.aggregate.workflow.Lane;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.usecase.board.create.CreateBoardUseCaseInput;
import domain.usecase.board.create.CreateBoardUseCaseOutputImpl;
import domain.usecase.board.repository.IBoardRepository;
import domain.usecase.workflow.create.CreateWorkflowUseCase;
import domain.usecase.workflow.create.CreateWorkflowUseCaseInput;
import domain.usecase.workflow.create.CreateWorkflowUseCaseOutput;
import domain.usecase.workflow.repository.IWorkflowRepository;
import org.junit.*;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateStageUseCaseTest {
    private IWorkflowRepository workflowRepository;
    private IBoardRepository boardRepository;
    private CreateWorkflowUseCase createWorkflowUseCase;
    private CreateWorkflowUseCaseOutput workflowOutput;
    private DomainEventBus eventBus;

    @Before
    public void SetUp(){
        eventBus = new DomainEventBus();
        boardRepository = new MySqlBoardRepository();

        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
        CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
        CreateBoardUseCaseOutputImpl createBoardUseCaseOutputImpl = new CreateBoardUseCaseOutputImpl();
        createBoardUseCaseInput.setBoardName("Kanban of KanbanDevelopment");
        createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutputImpl);

//        workflowRepository = new InMemoryWorkflowRepository();
        workflowRepository = new MySqlWorkflowRepository();
        createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository, eventBus);
        CreateWorkflowUseCaseInput input = new CreateWorkflowUseCaseInput();
        workflowOutput = new CreateWorkflowUseCaseOutput();
        input.setWorkflowName("KanbanDevelopment");
        input.setBoardId(createBoardUseCaseOutputImpl.getBoardId());

        createWorkflowUseCase.execute(input, workflowOutput);
    }

    @Test
    public void CreateStageTest() {
        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository);
        CreateStageUseCaseInput input = new CreateStageUseCaseInput();
        CreateStageUseCaseOutput output = new CreateStageUseCaseOutput();
        input.setWorkflowId(workflowOutput.getWorkflowId());
        input.setStageName("Doing");

        createStageUseCase.execute(input,output);

        assertEquals(workflowOutput.getWorkflowId(), output.getWorkflowId());
        assertNotNull(output.getStageId());
        assertEquals("Doing", output.getStageName());

        Workflow workflow = workflowRepository.getWorkflowById(workflowOutput.getWorkflowId());
        Lane lane = workflow.getLaneById(output.getStageId());

        assertEquals(output.getStageId(), lane.getLaneId());
        assertEquals(output.getStageName(), lane.getLaneName());
    }
}
