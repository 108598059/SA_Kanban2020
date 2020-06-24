package domain.usecase.task.create;

import domain.adapter.repository.board.MySqlBoardRepository;
import domain.adapter.repository.card.MySqlCardRepository;
import domain.adapter.repository.workflow.MySqlWorkflowRepository;
import domain.model.DomainEventBus;
import domain.model.aggregate.card.Card;
import domain.model.aggregate.card.Task;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.usecase.board.create.CreateBoardUseCaseInput;
import domain.usecase.board.create.CreateBoardUseCaseOutputImpl;
import domain.usecase.board.repository.IBoardRepository;
import domain.usecase.card.create.CreateCardUseCase;
import domain.usecase.card.create.CreateCardUseCaseInput;
import domain.usecase.card.create.CreateCardUseCaseOutput;
import domain.usecase.card.repository.ICardRepository;
import domain.usecase.stage.create.CreateStageUseCase;
import domain.usecase.stage.create.CreateStageUseCaseInput;
import domain.usecase.stage.create.CreateStageUseCaseOutput;
import domain.usecase.workflow.create.CreateWorkflowUseCase;
import domain.usecase.workflow.create.CreateWorkflowUseCaseInput;
import domain.usecase.workflow.create.CreateWorkflowUseCaseOutput;
import domain.usecase.workflow.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateTaskUseCaseTest {
    private IWorkflowRepository workflowRepository;
    private ICardRepository cardRepository;
    private IBoardRepository boardRepository;

    private CreateWorkflowUseCase createWorkflowUseCase;
    private CreateWorkflowUseCaseOutput workflowOutput;
    private CreateStageUseCase createStageUseCase;
    private CreateStageUseCaseOutput stageOutput;
    private CreateCardUseCaseOutput cardOutput;

    private DomainEventBus eventBus;

    @Before
    public void SetUp() throws CloneNotSupportedException {
//        workflowRepository = new InMemoryWorkflowRepository();
        workflowRepository = new MySqlWorkflowRepository();
        cardRepository = new MySqlCardRepository();
        boardRepository = new MySqlBoardRepository();

        eventBus = new DomainEventBus();

        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository, eventBus);
        CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
        CreateBoardUseCaseOutputImpl createBoardUseCaseOutputImpl = new CreateBoardUseCaseOutputImpl();
        createBoardUseCaseInput.setBoardName("Kanban of KanbanDevelopment");
        createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutputImpl);

        createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository, eventBus);
        CreateWorkflowUseCaseInput workflowInput = new CreateWorkflowUseCaseInput();
        workflowOutput = new CreateWorkflowUseCaseOutput();
        workflowInput.setWorkflowName("KanbanDevelopment");
        workflowInput.setBoardId(createBoardUseCaseOutputImpl.getBoardId());
        createWorkflowUseCase.execute(workflowInput, workflowOutput);

        createStageUseCase = new CreateStageUseCase(workflowRepository,eventBus);
        stageOutput = new CreateStageUseCaseOutput();
        CreateStageUseCaseInput stageInput = new CreateStageUseCaseInput();
        stageInput.setStageName("ToDo");
        stageInput.setWorkflowId(workflowOutput.getWorkflowId());
        createStageUseCase.execute(stageInput, stageOutput);

        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, eventBus);
        CreateCardUseCaseInput cardInput = new CreateCardUseCaseInput();
        cardOutput = new CreateCardUseCaseOutput();
        cardInput.setCardName("CreateCard");
        cardInput.setLaneId(stageOutput.getStageId());
        cardInput.setWorkflowId(stageOutput.getWorkflowId());
        createCardUseCase.execute(cardInput, cardOutput);
    }

    @Test
    public void createTaskUseCase() throws CloneNotSupportedException {
        CreateTaskUseCase createTaskUseCase = new CreateTaskUseCase(cardRepository,eventBus);
        CreateTaskUseCaseInput createTaskUseCaseInput = new CreateTaskUseCaseInput();
        CreateTaskUseCaseOutput createTaskUseCaseOutput = new CreateTaskUseCaseOutput();

        createTaskUseCaseInput.setCardId(cardOutput.getCardId());
        createTaskUseCaseInput.setTaskName("CreateTask");

        createTaskUseCase.execute(createTaskUseCaseInput, createTaskUseCaseOutput);

        assertEquals(cardOutput.getCardId(), createTaskUseCaseOutput.getCardId());
        assertNotNull(createTaskUseCaseOutput.getTaskId());
        assertEquals("CreateTask", createTaskUseCaseOutput.getTaskName());

        Card card = cardRepository.getCardById(cardOutput.getCardId());
        Task task = card.getTaskById(createTaskUseCaseOutput.getTaskId());

        assertEquals(createTaskUseCaseOutput.getTaskId(), task.getTaskId());
        assertEquals(createTaskUseCaseOutput.getCardId(), task.getCardId());
        assertEquals(createTaskUseCaseOutput.getTaskName(), task.getTaskName());
    }
}
