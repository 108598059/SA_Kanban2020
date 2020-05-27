package domain.usecase.card.cycleTime;

import domain.adapter.repository.board.InMemoryBoardRepository;
import domain.adapter.repository.board.MySqlBoardRepository;
import domain.adapter.repository.card.InMemoryCardRepository;
import domain.adapter.repository.card.MySqlCardRepository;
import domain.adapter.repository.flowEvent.ImMemoryFlowEventRepository;
import domain.adapter.repository.workflow.InMemoryWorkflowRepository;
import domain.adapter.repository.workflow.MySqlWorkflowRepository;
import domain.model.aggregate.DomainEventBus;
import domain.model.common.DateProvider;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.usecase.board.create.CreateBoardUseCaseInput;
import domain.usecase.board.create.CreateBoardUseCaseOutputImpl;
import domain.usecase.board.repository.IBoardRepository;
import domain.usecase.card.move.MoveCardUseCase;
import domain.usecase.card.move.MoveCardUseCaseInput;
import domain.usecase.card.move.MoveCardUseCaseOutput;
import domain.usecase.flowEvent.repository.IFlowEventRepository;
import domain.usecase.handler.card.CardEventHandler;
import domain.usecase.card.create.CreateCardUseCase;
import domain.usecase.card.create.CreateCardUseCaseInput;
import domain.usecase.card.create.CreateCardUseCaseOutput;
import domain.usecase.card.repository.ICardRepository;
import domain.usecase.handler.domainEvent.FlowEventHandler;
import domain.usecase.stage.create.CreateStageUseCase;
import domain.usecase.stage.create.CreateStageUseCaseInput;
import domain.usecase.stage.create.CreateStageUseCaseOutput;
import domain.usecase.workflow.WorkflowEventHandler;
import domain.usecase.workflow.create.CreateWorkflowUseCase;
import domain.usecase.workflow.create.CreateWorkflowUseCaseInput;
import domain.usecase.workflow.create.CreateWorkflowUseCaseOutput;
import domain.usecase.workflow.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class CalculateCycleTimeUseCaseTest {
    private IWorkflowRepository workflowRepository;
    private IBoardRepository boardRepository;
    private ICardRepository cardRepository;
    private IFlowEventRepository flowEventRepository;

    private CreateWorkflowUseCase createWorkflowUseCase;
    private CreateWorkflowUseCaseOutput workflowOutput;
    private CreateStageUseCase createStageUseCase;
    private CreateStageUseCaseOutput stageOutput1;
    private CreateStageUseCaseOutput stageOutput2;
    private CreateStageUseCaseOutput stageOutput3;
    private CreateCardUseCaseOutput cardOutput;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private DomainEventBus eventBus;

    @Before
    public void SetUp() throws Exception {
        DateProvider.setDate(dateFormat.parse("2020/5/24 00:00:00"));

        boardRepository = new InMemoryBoardRepository();
        workflowRepository = new InMemoryWorkflowRepository();
        cardRepository = new InMemoryCardRepository();
        flowEventRepository = new ImMemoryFlowEventRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new WorkflowEventHandler(boardRepository, eventBus));
        eventBus.register(new FlowEventHandler(flowEventRepository));
        eventBus.register(new CardEventHandler(workflowRepository, eventBus));

        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
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

        createStageUseCase = new CreateStageUseCase(workflowRepository);
        stageOutput1 = new CreateStageUseCaseOutput();
        CreateStageUseCaseInput stageInput = new CreateStageUseCaseInput();
        stageInput.setStageName("ToDo");
        stageInput.setWorkflowId(workflowOutput.getWorkflowId());
        createStageUseCase.execute(stageInput, stageOutput1);

        createStageUseCase = new CreateStageUseCase(workflowRepository);
        stageOutput2 = new CreateStageUseCaseOutput();
        stageInput = new CreateStageUseCaseInput();
        stageInput.setStageName("Doing");
        stageInput.setWorkflowId(workflowOutput.getWorkflowId());
        createStageUseCase.execute(stageInput, stageOutput2);

        createStageUseCase = new CreateStageUseCase(workflowRepository);
        stageOutput3 = new CreateStageUseCaseOutput();
        stageInput = new CreateStageUseCaseInput();
        stageInput.setStageName("Done");
        stageInput.setWorkflowId(workflowOutput.getWorkflowId());
        createStageUseCase.execute(stageInput, stageOutput3);

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
    public void move_card_from_Todo_to_Done() throws ParseException {
        DateProvider.setDate(dateFormat.parse("2020/5/25 01:01:01"));

        MoveCardUseCase moveCardUseCase = new MoveCardUseCase(workflowRepository, eventBus);
        MoveCardUseCaseInput moveCardInput = new MoveCardUseCaseInput();
        MoveCardUseCaseOutput moveCardOutput = new MoveCardUseCaseOutput();

        moveCardInput.setWorkflowId(workflowOutput.getWorkflowId());
        moveCardInput.setCardId(cardOutput.getCardId());
        moveCardInput.setFromLaneId(stageOutput1.getStageId());
        moveCardInput.setToLaneId(stageOutput2.getStageId());

        moveCardUseCase.execute(moveCardInput, moveCardOutput);

        CalculateCycleTimeUseCase calculateCycleTimeUseCase = new CalculateCycleTimeUseCase(workflowRepository, flowEventRepository);
        CalculateCycleTimeInput calculateCycleTimeInput = new CalculateCycleTimeInput();
        CalculateCycleTimeOutput calculateCycleTimeOutput = new CalculateCycleTimeOutput();

        calculateCycleTimeInput.setWorkflowId(workflowOutput.getWorkflowId());
        calculateCycleTimeInput.setFromLaneId(stageOutput1.getStageId());
        calculateCycleTimeInput.setToLaneId(stageOutput2.getStageId());
        calculateCycleTimeInput.setCardId(cardOutput.getCardId());

        calculateCycleTimeUseCase.execute(calculateCycleTimeInput, calculateCycleTimeOutput);

        assertEquals(1, calculateCycleTimeOutput.getCycleTime().getDiffDays());
        assertEquals(1, calculateCycleTimeOutput.getCycleTime().getDiffHours());
        assertEquals(1, calculateCycleTimeOutput.getCycleTime().getDiffMinutes());
        assertEquals(1, calculateCycleTimeOutput.getCycleTime().getDiffSeconds());

        DateProvider.setDate(dateFormat.parse("2020/5/26 02:02:02"));

        moveCardUseCase = new MoveCardUseCase(workflowRepository, eventBus);
        moveCardInput = new MoveCardUseCaseInput();
        moveCardOutput = new MoveCardUseCaseOutput();

        moveCardInput.setWorkflowId(workflowOutput.getWorkflowId());
        moveCardInput.setCardId(cardOutput.getCardId());
        moveCardInput.setFromLaneId(stageOutput2.getStageId());
        moveCardInput.setToLaneId(stageOutput3.getStageId());

        moveCardUseCase.execute(moveCardInput, moveCardOutput);

        calculateCycleTimeUseCase = new CalculateCycleTimeUseCase(workflowRepository, flowEventRepository);
        calculateCycleTimeInput = new CalculateCycleTimeInput();
        calculateCycleTimeOutput = new CalculateCycleTimeOutput();

        calculateCycleTimeInput.setWorkflowId(workflowOutput.getWorkflowId());
        calculateCycleTimeInput.setFromLaneId(stageOutput1.getStageId());
        calculateCycleTimeInput.setToLaneId(stageOutput3.getStageId());
        calculateCycleTimeInput.setCardId(cardOutput.getCardId());

        calculateCycleTimeUseCase.execute(calculateCycleTimeInput, calculateCycleTimeOutput);

        assertEquals(2, calculateCycleTimeOutput.getCycleTime().getDiffDays());
        assertEquals(2, calculateCycleTimeOutput.getCycleTime().getDiffHours());
        assertEquals(2, calculateCycleTimeOutput.getCycleTime().getDiffMinutes());
        assertEquals(2, calculateCycleTimeOutput.getCycleTime().getDiffSeconds());
    }
}
