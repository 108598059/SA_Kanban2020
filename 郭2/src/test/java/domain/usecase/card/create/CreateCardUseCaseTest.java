package domain.usecase.card.create;

import domain.adapter.repository.workflow.MySqlWorkflowRepository;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.usecase.board.create.CreateBoardUseCaseInput;
import domain.usecase.board.create.CreateBoardUseCaseOutput;
import domain.adapter.repository.board.InMemoryBoardRepository;
import domain.adapter.repository.card.InMemoryCardRepository;
import domain.usecase.board.repository.IBoardRepository;
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

import static org.junit.Assert.*;

public class CreateCardUseCaseTest {
    private IWorkflowRepository workflowRepository;
    private IBoardRepository boardRepository;
    private ICardRepository cardRepository;

    private CreateWorkflowUseCase createWorkflowUseCase;
    private CreateWorkflowUseCaseOutput workflowOutput;
    private CreateStageUseCase createStageUseCase;
    private CreateStageUseCaseOutput stageOutput;

    @Before
    public void SetUp(){
        boardRepository = new InMemoryBoardRepository();
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
        CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
        CreateBoardUseCaseOutput createBoardUseCaseOutput = new CreateBoardUseCaseOutput();
        createBoardUseCaseInput.setBoardName("Kanban of KanbanDevelopment");
        createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutput);

        workflowRepository = new MySqlWorkflowRepository();
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
    }
    @Test
    public void CreateCardUseCaseTest(){
        cardRepository = new InMemoryCardRepository();
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(workflowRepository, cardRepository);
        CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
        CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardUseCaseOutput();
        createCardUseCaseInput.setCardName("CreateCard");
        createCardUseCaseInput.setStageId(stageOutput.getStageId());
        createCardUseCaseInput.setWorkflowId(stageOutput.getWorkflowId());

        createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);

        assertEquals("CreateCard",createCardUseCaseOutput.getCardName());
        assertNotNull(createCardUseCaseOutput.getCardId());
    }

}
