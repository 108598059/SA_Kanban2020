package domain.usecase.swimlane.create;
import domain.adapter.repository.board.MySqlBoardRepository;
import domain.adapter.repository.workflow.MySqlWorkflowRepository;
import domain.model.DomainEventBus;
import domain.model.aggregate.workflow.Lane;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.usecase.board.create.CreateBoardUseCaseInput;
import domain.usecase.board.create.CreateBoardUseCaseOutputImpl;
import domain.usecase.board.repository.IBoardRepository;
import domain.usecase.workflow.WorkflowTransfer;
import domain.usecase.workflow.create.CreateWorkflowUseCase;
import domain.usecase.workflow.create.CreateWorkflowUseCaseInput;
import domain.usecase.workflow.create.CreateWorkflowUseCaseOutput;
import domain.usecase.workflow.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class CreateSwimlaneUseCaseTest {
    private IWorkflowRepository workflowRepository;
    private IBoardRepository boardRepository;
    private CreateWorkflowUseCase createWorkflowUseCase;
    private CreateWorkflowUseCaseOutput workflowOutput;
    private DomainEventBus eventBus;
    @Before
    public void SetUp(){
        boardRepository = new MySqlBoardRepository();
        eventBus = new DomainEventBus();

        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository, eventBus);
        CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
        CreateBoardUseCaseOutputImpl createBoardUseCaseOutputImpl = new CreateBoardUseCaseOutputImpl();
        createBoardUseCaseInput.setBoardName("Kanban of KanbanDevelopment");
        createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutputImpl);

//        workflowRepository = new InMemoryWorkflowRepository();
        workflowRepository = new MySqlWorkflowRepository();
        createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository, eventBus);
        CreateWorkflowUseCaseInput input = new CreateWorkflowUseCaseInput();
        workflowOutput = new CreateWorkflowUseCaseOutput();
        input.setBoardId(createBoardUseCaseOutputImpl.getBoardId());
        input.setWorkflowName("KanbanDevelopment");

        createWorkflowUseCase.execute(input, workflowOutput);
    }

    @Test
    public void createSwimlaneUseCase() throws CloneNotSupportedException {
        CreateSwimlaneUseCase createStageUseCase = new CreateSwimlaneUseCase(workflowRepository,eventBus);
        CreateSwimlaneUseCaseInput input = new CreateSwimlaneUseCaseInput();
        CreateSwimlaneUseCaseOutput output = new CreateSwimlaneUseCaseOutput();

        input.setWorkflowId(workflowOutput.getWorkflowId());
        input.setSwimlaneName("Emergency");

        createStageUseCase.execute(input,output);

        assertEquals(workflowOutput.getWorkflowId(), output.getWorkflowId());
        assertNotNull(output.getSwimlaneId());
        assertEquals("Emergency", output.getSwimlaneName());

        Workflow workflow = WorkflowTransfer.WorkflowDTOToWorkflow(workflowRepository.getWorkflowById(workflowOutput.getWorkflowId()));
        Lane lane = workflow.getLaneById(output.getSwimlaneId());

        assertEquals(output.getSwimlaneId(), lane.getLaneId());
        assertEquals(output.getSwimlaneName(), lane.getLaneName());
    }
}
