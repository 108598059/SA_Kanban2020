package domain.usecase.swimlane.create;
import domain.adapter.repository.board.MySqlBoardRepository;
import domain.adapter.repository.workflow.MySqlWorkflowRepository;
import domain.model.aggregate.DomainEventBus;
import domain.model.aggregate.workflow.Lane;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.usecase.board.create.CreateBoardUseCaseInput;
import domain.usecase.board.create.CreateBoardUseCaseOutput;
import domain.usecase.board.repository.IBoardRepository;
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

        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
        CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
        CreateBoardUseCaseOutput createBoardUseCaseOutput = new CreateBoardUseCaseOutput();
        createBoardUseCaseInput.setBoardName("Kanban of KanbanDevelopment");
        createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutput);

//        workflowRepository = new InMemoryWorkflowRepository();
        workflowRepository = new MySqlWorkflowRepository();
        createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository, boardRepository, eventBus);
        CreateWorkflowUseCaseInput input = new CreateWorkflowUseCaseInput();
        workflowOutput = new CreateWorkflowUseCaseOutput();
        input.setBoardId(createBoardUseCaseOutput.getBoardId());
        input.setWorkflowName("KanbanDevelopment");

        createWorkflowUseCase.execute(input, workflowOutput);
    }

    @Test
    public void CreateSwimlaneUseCase(){
        CreateSwimlaneUseCase createStageUseCase = new CreateSwimlaneUseCase(workflowRepository);
        CreateSwimlaneUseCaseInput input = new CreateSwimlaneUseCaseInput();
        CreateSwimlaneUseCaseOutput output = new CreateSwimlaneUseCaseOutput();

        input.setWorkflowId(workflowOutput.getWorkflowId());
        input.setSwimlaneName("Emergency");

        createStageUseCase.execute(input,output);

        assertEquals(workflowOutput.getWorkflowId(), output.getWorkflowId());
        assertNotNull(output.getSwimlaneId());
        assertEquals("Emergency", output.getSwimlaneName());

        Workflow workflow = workflowRepository.getWorkflowById(workflowOutput.getWorkflowId());
        Lane lane = workflow.getLaneById(output.getSwimlaneId());

        assertEquals(output.getSwimlaneId(), lane.getLaneId());
        assertEquals(output.getSwimlaneName(), lane.getLaneName());
    }
}
