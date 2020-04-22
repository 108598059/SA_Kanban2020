package domain.usecase.workflow.create;
import domain.adapter.repository.workflow.MySqlWorkflowRepository;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.usecase.board.create.CreateBoardUseCaseInput;
import domain.usecase.board.create.CreateBoardUseCaseOutput;
import domain.adapter.repository.board.InMemoryBoardRepository;
import domain.adapter.repository.workflow.InMemoryWorkflowRepository;


import domain.usecase.board.repository.IBoardRepository;
import domain.usecase.workflow.repository.IWorkflowRepository;
import org.junit.*;

import static org.junit.Assert.*;

public class CreateWorkflowUseCaseTest {
    private IBoardRepository boardRepository;
    private CreateBoardUseCaseOutput createBoardUseCaseOutput;
    private IWorkflowRepository workflowRepository;

    @Before
    public void SetUp(){
        boardRepository = new InMemoryBoardRepository();
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
        CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
        createBoardUseCaseOutput = new CreateBoardUseCaseOutput();

        createBoardUseCaseInput.setBoardName("Kanban of KanbanDevelopment");

        createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutput);
    }

    @Test
    public void createWorkflowTest(){
//        workflowRepository = new InMemoryWorkflowRepository();
        workflowRepository = new MySqlWorkflowRepository();
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository, boardRepository);
        CreateWorkflowUseCaseInput input = new CreateWorkflowUseCaseInput();
        CreateWorkflowUseCaseOutput output = new CreateWorkflowUseCaseOutput();

        input.setWorkflowName("KanbanDevelopment");
        input.setBoardId(createBoardUseCaseOutput.getBoardId());

        createWorkflowUseCase.execute(input, output);

        assertNotNull(output.getWorkflowId());
        assertEquals("KanbanDevelopment", output.getWorkflowName());
    }
}
