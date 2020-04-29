package kanban.domain.usecase;

import com.google.common.eventbus.EventBus;
import kanban.domain.Utility;
import kanban.domain.adapter.repository.board.MySqlBoardRepository;
import kanban.domain.adapter.repository.workflow.MySqlWorkflowRepository;
import kanban.domain.model.DomainEventBus;
import kanban.domain.usecase.board.repository.IBoardRepository;
import kanban.domain.usecase.workflow.create.CreateWorkflowInput;
import kanban.domain.usecase.workflow.create.CreateWorkflowOutput;
import kanban.domain.usecase.workflow.create.CreateWorkflowUseCase;
import kanban.domain.usecase.workflow.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateWorkflowTest {

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;
    private Utility utility;
    private String boardId;

    @Before
    public void setUp() {
//        boardRepository = new InMemoryBoardRepository();
//        workflowRepository = new InMemoryWorkflowRepository();

        boardRepository = new MySqlBoardRepository();
        workflowRepository = new MySqlWorkflowRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(
                boardRepository,
                workflowRepository));

        utility = new Utility(boardRepository, workflowRepository, eventBus);
        boardId = utility.createBoard("test automation");
    }

    @Test
    public void Create_workflow_should_success() {
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(
                workflowRepository,
                eventBus
        );

        CreateWorkflowInput input = new CreateWorkflowInput();
        CreateWorkflowOutput output = new CreateWorkflowOutput();

        input.setBoardId(boardId);
        input.setWorkflowName("workflow");
        createWorkflowUseCase.execute(input, output);
        assertNotNull(output.getWorkflowId());
        assertEquals("workflow", output.getWorkflowName());
    }

    @Test(expected = RuntimeException.class)
    public void Workflow_repository_should_throw_exception() {
        utility.createWorkflow(boardId, "workflowName");
        workflowRepository.getWorkflowById("123-456-789");
    }
}
