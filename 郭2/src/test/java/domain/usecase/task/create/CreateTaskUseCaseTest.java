package domain.usecase.task.create;

import domain.usecase.board.create.CreateBoardUseCase;
import domain.usecase.board.create.CreateBoardUseCaseInput;
import domain.usecase.board.create.CreateBoardUseCaseOutput;
import domain.adapter.repository.board.InMemoryBoardRepository;
import domain.usecase.board.repository.IBoardRepository;
import domain.usecase.card.create.CreateCardUseCase;
import domain.usecase.card.create.CreateCardUseCaseInput;
import domain.usecase.card.create.CreateCardUseCaseOutput;
import domain.adapter.repository.card.InMemoryCardRepository;
import domain.usecase.card.repository.ICardRepository;
import domain.usecase.stage.create.CreateStageUseCase;
import domain.usecase.stage.create.CreateStageUseCaseInput;
import domain.usecase.stage.create.CreateStageUseCaseOutput;
import domain.usecase.workflow.create.CreateWorkflowUseCase;
import domain.usecase.workflow.create.CreateWorkflowUseCaseInput;
import domain.usecase.workflow.create.CreateWorkflowUseCaseOutput;
import domain.adapter.repository.workflow.InMemoryWorkflowRepository;
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

    @Before
    public void SetUp(){
        workflowRepository = new InMemoryWorkflowRepository();
        cardRepository = new InMemoryCardRepository();
        boardRepository = new InMemoryBoardRepository();

        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
        CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
        CreateBoardUseCaseOutput createBoardUseCaseOutput = new CreateBoardUseCaseOutput();
        createBoardUseCaseInput.setBoardName("Kanban of KanbanDevelopment");
        createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutput);

        createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository, boardRepository);
        CreateWorkflowUseCaseInput workflowInput = new CreateWorkflowUseCaseInput();
        workflowOutput = new CreateWorkflowUseCaseOutput();
        workflowInput.setWorkflowName("KanbanDevelopment");
        workflowInput.setBoardId(createBoardUseCaseOutput.getBoardId());
        createWorkflowUseCase.execute(workflowInput, workflowOutput);

        createStageUseCase = new CreateStageUseCase(workflowRepository);
        stageOutput = new CreateStageUseCaseOutput();
        CreateStageUseCaseInput stageInput = new CreateStageUseCaseInput();
        stageInput.setStageName("ToDo");
        stageInput.setWorkflowId(workflowOutput.getWorkflowId());
        createStageUseCase.execute(stageInput, stageOutput);

        CreateCardUseCase createCardUseCase = new CreateCardUseCase(workflowRepository, cardRepository);
        CreateCardUseCaseInput cardInput = new CreateCardUseCaseInput();
        cardOutput = new CreateCardUseCaseOutput();
        cardInput.setCardName("CreateCard");
        cardInput.setStageId(stageOutput.getStageId());
        cardInput.setWorkflowId(stageOutput.getWorkflowId());
        createCardUseCase.execute(cardInput, cardOutput);
    }

    @Test
    public void CreateTaskUseCaseTest(){
        CreateTaskUseCase createTaskUseCase = new CreateTaskUseCase(cardRepository);
        CreateTaskUseCaseInput createTaskUseCaseInput = new CreateTaskUseCaseInput();
        CreateTaskUseCaseOutput createTaskUseCaseOutput = new CreateTaskUseCaseOutput();

        createTaskUseCaseInput.setCardId(cardOutput.getCardId());
        createTaskUseCaseInput.setTaskName("CreateTask");

        createTaskUseCase.execute(createTaskUseCaseInput, createTaskUseCaseOutput);

        assertEquals(cardOutput.getCardId(), createTaskUseCaseOutput.getCardId());
        assertNotNull(createTaskUseCaseOutput.getTaskId());
        assertEquals("CreateTask", createTaskUseCaseOutput.getTaskName());
    }
}
