package domain.usecase.card.create;

import domain.adapter.repository.board.MySqlBoardRepository;
import domain.adapter.repository.card.MySqlCardRepository;
import domain.adapter.repository.workflow.MySqlWorkflowRepository;
import domain.model.DomainEventBus;
import domain.model.aggregate.card.Card;
import domain.model.aggregate.workflow.Lane;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.usecase.board.create.CreateBoardUseCaseInput;
import domain.usecase.board.create.CreateBoardUseCaseOutputImpl;
import domain.usecase.board.repository.IBoardRepository;
import domain.usecase.handler.card.CardEventHandler;
import domain.usecase.card.repository.ICardRepository;
import domain.usecase.stage.create.CreateStageUseCase;
import domain.usecase.stage.create.CreateStageUseCaseInput;
import domain.usecase.stage.create.CreateStageUseCaseOutput;
import domain.usecase.handler.workflow.WorkflowEventHandler;
import domain.usecase.workflow.create.CreateWorkflowUseCase;
import domain.usecase.workflow.create.CreateWorkflowUseCaseInput;
import domain.usecase.workflow.create.CreateWorkflowUseCaseOutput;
import domain.usecase.workflow.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CreateCardUseCaseTest {
    private IWorkflowRepository workflowRepository;
    private IBoardRepository boardRepository;
    private ICardRepository cardRepository;

    private CreateWorkflowUseCase createWorkflowUseCase;
    private CreateWorkflowUseCaseOutput workflowOutput;
    private CreateStageUseCase createStageUseCase;
    private CreateStageUseCaseOutput stageOutput;

    private DomainEventBus eventBus;

    @Before
    public void SetUp() throws CloneNotSupportedException {
        eventBus = new DomainEventBus();

        boardRepository = new MySqlBoardRepository();
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository,eventBus);
        CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
        CreateBoardUseCaseOutputImpl createBoardUseCaseOutputImpl = new CreateBoardUseCaseOutputImpl();
        createBoardUseCaseInput.setBoardName("Kanban of KanbanDevelopment");
        createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutputImpl);

        eventBus.register(new WorkflowEventHandler(boardRepository, eventBus));

        workflowRepository = new MySqlWorkflowRepository();
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

        eventBus.register(new CardEventHandler(workflowRepository, eventBus));
    }
    @Test
    public void create_card_should_commit_it_to_its_lane_of_the_workflow() throws CloneNotSupportedException {
        cardRepository = new MySqlCardRepository();
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, eventBus);
        CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
        CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardUseCaseOutput();

        createCardUseCaseInput.setCardName("CreateCard");
        createCardUseCaseInput.setCardContent("implement card class");
        createCardUseCaseInput.setCardType("People Development");
        createCardUseCaseInput.setLaneId(stageOutput.getStageId());
        createCardUseCaseInput.setWorkflowId(stageOutput.getWorkflowId());

        createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);

        assertEquals("CreateCard",createCardUseCaseOutput.getCardName());
        assertEquals("implement card class", createCardUseCaseOutput.getCardContent());
        assertEquals("People Development", createCardUseCaseOutput.getCardType());
        assertNotNull(createCardUseCaseOutput.getCardId());

        Card card = cardRepository.getCardById(createCardUseCaseOutput.getCardId());

        assertEquals(createCardUseCaseOutput.getCardId(), card.getCardId());
        assertEquals(createCardUseCaseOutput.getCardName(), card.getCardName());

        workflowRepository = new MySqlWorkflowRepository();
        Workflow workflow = workflowRepository.getWorkflowById(workflowOutput.getWorkflowId());

        Lane lane = workflow.getLaneById(stageOutput.getStageId());
        List<String> cardList = lane.getCardIdList();

        assertTrue(cardList.contains(createCardUseCaseOutput.getCardId()));
    }
}
