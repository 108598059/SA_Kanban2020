package kanban.domain.usecase;

import kanban.domain.Utility;
import kanban.domain.adapter.repository.board.MySqlBoardRepository;
import kanban.domain.adapter.repository.workflow.MySqlWorkflowRepository;
import kanban.domain.model.aggregate.workflow.Stage;
import kanban.domain.model.aggregate.workflow.Workflow;
import kanban.domain.usecase.board.repository.IBoardRepository;
import kanban.domain.usecase.stage.create.CreateStageInput;
import kanban.domain.usecase.stage.create.CreateStageOutput;
import kanban.domain.usecase.stage.create.CreateStageUseCase;
import kanban.domain.usecase.workflow.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateStageTest {
    private String boardId;
    private String workflowId;
    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private Utility utility;

    @Before
    public void setup() {
//        boardRepository = new InMemoryBoardRepository();
//        workflowRepository = new InMemoryWorkflowRepository();
        boardRepository = new MySqlBoardRepository();
        workflowRepository = new MySqlWorkflowRepository();

        utility = new Utility(boardRepository, workflowRepository);
        boardId = utility.createBoard("test automation");
        workflowId = utility.createWorkflow(boardId, "workflowName");
    }

    @Test
    public void Create_stage_should_success() {
        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository);
        CreateStageInput input = new CreateStageInput();
        input.setStageName("stage");
        input.setWorkflowId(workflowId);
        CreateStageOutput output = new CreateStageOutput();

        createStageUseCase.execute(input, output);

        Workflow workflow = workflowRepository.getWorkflowById(workflowId);
        Stage cloneStage = workflow.getStageCloneById(output.getStageId());
        assertEquals(output.getStageName(), cloneStage.getName());
    }

    @Test(expected = RuntimeException.class)
    public void GetStageNameById_should_throw_exception() {
        Workflow workflow = workflowRepository.getWorkflowById(workflowId);
        workflow.getStageCloneById("123-456-789");
    }
}
