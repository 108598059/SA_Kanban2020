package domain.usecase.card.move;

import domain.adapter.repository.board.MySqlBoardRepository;
import domain.adapter.repository.card.MySqlCardRepository;
import domain.adapter.repository.workflow.MySqlWorkflowRepository;
import domain.model.DomainEventBus;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.usecase.board.create.CreateBoardUseCaseInput;
import domain.usecase.board.create.CreateBoardUseCaseOutputImpl;
import domain.usecase.board.repository.IBoardRepository;
import domain.usecase.handler.card.CardEventHandler;
import domain.usecase.card.create.CreateCardUseCase;
import domain.usecase.card.create.CreateCardUseCaseInput;
import domain.usecase.card.create.CreateCardUseCaseOutput;
import domain.usecase.card.repository.ICardRepository;
import domain.usecase.stage.create.CreateStageUseCase;
import domain.usecase.stage.create.CreateStageUseCaseInput;
import domain.usecase.stage.create.CreateStageUseCaseOutput;
import domain.usecase.handler.workflow.WorkflowEventHandler;
import domain.usecase.workflow.WorkflowTransfer;
import domain.usecase.workflow.create.CreateWorkflowUseCase;
import domain.usecase.workflow.create.CreateWorkflowUseCaseInput;
import domain.usecase.workflow.create.CreateWorkflowUseCaseOutput;
import domain.usecase.workflow.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoveCardUseCaseTest {
    private IWorkflowRepository workflowRepository;
    private IBoardRepository boardRepository;
    private ICardRepository cardRepository;

    private CreateWorkflowUseCase createWorkflowUseCase;
    private CreateWorkflowUseCaseOutput workflowOutput;
    private CreateStageUseCase createStageUseCase;
    private CreateStageUseCaseOutput stageOutput1;
    private CreateStageUseCaseOutput stageOutput2;
    private CreateCardUseCaseOutput cardOutput;

    private DomainEventBus eventBus;

    @Before
    public void SetUp() throws CloneNotSupportedException {
        eventBus = new DomainEventBus();

        boardRepository = new MySqlBoardRepository();
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository, eventBus);
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
        stageOutput1 = new CreateStageUseCaseOutput();
        CreateStageUseCaseInput stageInput = new CreateStageUseCaseInput();
        stageInput.setStageName("ToDo");
        stageInput.setWorkflowId(workflowOutput.getWorkflowId());
        createStageUseCase.execute(stageInput, stageOutput1);

        createStageUseCase = new CreateStageUseCase(workflowRepository,eventBus);
        stageOutput2 = new CreateStageUseCaseOutput();
        stageInput = new CreateStageUseCaseInput();
        stageInput.setStageName("Doing");
        stageInput.setWorkflowId(workflowOutput.getWorkflowId());
        createStageUseCase.execute(stageInput, stageOutput2);

        eventBus.register(new CardEventHandler(workflowRepository, eventBus));

        cardRepository = new MySqlCardRepository();
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, eventBus);
        CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
        cardOutput = new CreateCardUseCaseOutput();

        createCardUseCaseInput.setCardName("CreateCard");
        createCardUseCaseInput.setCardContent("implement card class");
        createCardUseCaseInput.setCardType("People Development");
        createCardUseCaseInput.setLaneId(stageOutput1.getStageId());
        createCardUseCaseInput.setWorkflowId(stageOutput1.getWorkflowId());

        createCardUseCase.execute(createCardUseCaseInput, cardOutput);
    }
    @Test
    public void move_card_should_move_it_from_original_lane_of_the_workflow_to_another_lane_of_the_workflow() throws CloneNotSupportedException {
        MoveCardUseCase moveCardUseCase = new MoveCardUseCase(workflowRepository, eventBus);
        MoveCardUseCaseInput input = new MoveCardUseCaseInput();
        MoveCardUseCaseOutput output = new MoveCardUseCaseOutput();

        input.setCardId(cardOutput.getCardId());
        input.setFromLaneId(stageOutput1.getStageId());
        input.setWorkflowId(stageOutput1.getWorkflowId());
        input.setToLaneId(stageOutput2.getStageId());

        moveCardUseCase.execute(input, output);

        assertEquals(workflowOutput.getWorkflowId(), output.getWorkflowId());

        assertFalse(WorkflowTransfer.WorkflowDTOToWorkflow(workflowRepository.getWorkflowById(workflowOutput.getWorkflowId())).getLaneById(stageOutput1.getStageId()).getCardIdList().contains(cardOutput.getCardId()));
        assertTrue(WorkflowTransfer.WorkflowDTOToWorkflow(workflowRepository.getWorkflowById(workflowOutput.getWorkflowId())).getLaneById(stageOutput2.getStageId()).getCardIdList().contains(cardOutput.getCardId()));
    }
}
