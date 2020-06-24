package ddd.kanban.usecase.card.cycleTime.calculate;

import ddd.kanban.adapter.repository.board.InMemoryBoardRepository;
import ddd.kanban.adapter.repository.card.InMemoryCardRepository;
import ddd.kanban.adapter.repository.flowevent.InMemoryFlowEventRepository;
import ddd.kanban.adapter.repository.workflow.InMemoryWorkflowRepository;
import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.card.event.CardCommitted;
import ddd.kanban.domain.model.card.event.CardUnCommitted;
import ddd.kanban.domain.model.common.DateProvider;
import ddd.kanban.usecase.HierarchyInitial;
import ddd.kanban.usecase.card.create.CreateCardInput;
import ddd.kanban.usecase.card.create.CreateCardOutput;
import ddd.kanban.usecase.card.create.CreateCardUseCase;
import ddd.kanban.usecase.handler.DomainEventHandler;
import ddd.kanban.usecase.handler.FlowEventHandler;
import ddd.kanban.usecase.repository.BoardRepository;
import ddd.kanban.usecase.repository.CardRepository;
import ddd.kanban.usecase.repository.FlowEventRepository;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.workflow.create.CreateColumnInput;
import ddd.kanban.usecase.workflow.create.CreateColumnOutput;
import ddd.kanban.usecase.workflow.create.CreateColumnUseCase;
import ddd.kanban.usecase.workflow.move.MoveCardInput;
import ddd.kanban.usecase.workflow.move.MoveCardOutput;
import ddd.kanban.usecase.workflow.move.MoveCardUseCase;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class CalculateCycleTimeUseCaseTest {
    private HierarchyInitial hierarchyInitial;

    private String boardId;
    private String workflowId;

    private String cardIdWillMove;

    private String beginningLaneId;
    private String analysisLaneId;
    private String developmentLaneId;
    private String testLaneId;
    private String readyToDeployLaneId;
    private String endLaneId;

    private DomainEventBus domainEventBus;
    private CardRepository cardRepository;
    private WorkflowRepository workflowRepository;
    private BoardRepository boardRepository;
    private FlowEventRepository flowEventRepository;

    private SimpleDateFormat dateFormat;

    @Before
    public void setUp(){
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        cardRepository = new InMemoryCardRepository();

        this.workflowRepository = new InMemoryWorkflowRepository();
        this.boardRepository = new InMemoryBoardRepository();
        this.flowEventRepository = new InMemoryFlowEventRepository();

        this.domainEventBus = new DomainEventBus();
        domainEventBus.register(new DomainEventHandler(workflowRepository, boardRepository, this.domainEventBus));
        domainEventBus.register(new FlowEventHandler(flowEventRepository));
        hierarchyInitial = new HierarchyInitial(boardRepository, workflowRepository, domainEventBus);

        this.boardId = hierarchyInitial.CreateBoard();
        this.workflowId = workflowRepository.findAll().get(0).getId();

        this.beginningLaneId = this.createColumn(this.workflowId, "Ready");
        this.analysisLaneId = this.createColumn(this.workflowId, "Analysis");
        this.developmentLaneId = this.createColumn(this.workflowId, "Development");
        this.testLaneId = this.createColumn(this.workflowId, "Test");
        this.readyToDeployLaneId = this.createColumn(this.workflowId, "Ready to Deploy");
        this.endLaneId = this.createColumn(this.workflowId, "Deployed");
    }

    @Test
    public void testCalculateCycleTimeUseCaseForSingleLane() throws ParseException {
        DateProvider.setDate(dateFormat.parse("2020/5/20 00:00:00"));
        this.cardIdWillMove = createCard("Implement Calculate Cycle Time UseCase");
        this.moveCard(workflowRepository.findById(this.workflowId).getLaneEntities().get(0).getId(), this.beginningLaneId, this.cardIdWillMove);
        DateProvider.setDate(dateFormat.parse("2020/5/23 00:00:00"));
        this.moveCard(this.beginningLaneId, this.analysisLaneId, this.cardIdWillMove);

        CalculateCycleTimeUseCase calculateCycleTimeUseCase = new CalculateCycleTimeUseCase(this.workflowRepository, this.flowEventRepository);
        CalculateCycleTimeInput calculateCycleTimeInput = new CalculateCycleTimeInput(this.cardIdWillMove, this.workflowId, this.beginningLaneId, this.beginningLaneId);
        CalculateCycleTimeOutput calculateCycleTimeOutput = new CalculateCycleTimeOutput();
        calculateCycleTimeUseCase.execute(calculateCycleTimeInput, calculateCycleTimeOutput);
        assertEquals(3, calculateCycleTimeOutput.getCycleTime().getDay());
    }

    @Test
    public void testCalculateCycleTimeUseCaseForCardJustUncommittedFromDefaultLane() throws ParseException {
        DateProvider.setDate(dateFormat.parse("2020/5/20 00:00:00"));
        String cardId = createCard("Buy house");
        this.moveCard(workflowRepository.findById(this.workflowId).getLaneEntities().get(0).getId(), this.beginningLaneId, cardId);
        DateProvider.setDate(dateFormat.parse("2020/5/23 00:00:00"));

        CalculateCycleTimeUseCase calculateCycleTimeUseCase = new CalculateCycleTimeUseCase(this.workflowRepository, this.flowEventRepository);
        CalculateCycleTimeInput calculateCycleTimeInput = new CalculateCycleTimeInput(cardId, this.workflowId, this.beginningLaneId, this.beginningLaneId);
        CalculateCycleTimeOutput calculateCycleTimeOutput = new CalculateCycleTimeOutput();

        calculateCycleTimeUseCase.execute(calculateCycleTimeInput, calculateCycleTimeOutput);

        assertEquals(3, calculateCycleTimeOutput.getCycleTime().getDay());
    }

    @Test
    public void testCalculateCycleTimeUseCaseForMoveCardFromReadyToDepoly() throws ParseException {
        this.simulateMoveCard();
        CalculateCycleTimeUseCase calculateCycleTimeUseCase = new CalculateCycleTimeUseCase(this.workflowRepository, this.flowEventRepository);
        CalculateCycleTimeInput calculateCycleTimeInput = new CalculateCycleTimeInput(this.cardIdWillMove, this.workflowId, this.beginningLaneId, this.readyToDeployLaneId);
        CalculateCycleTimeOutput calculateCycleTimeOutput = new CalculateCycleTimeOutput();

        calculateCycleTimeUseCase.execute(calculateCycleTimeInput, calculateCycleTimeOutput);

        assertEquals(5, calculateCycleTimeOutput.getCycleTime().getDay());
    }

    private void simulateMoveCard() throws ParseException {
        DateProvider.setDate(dateFormat.parse("2020/5/22 00:00:00"));
        this.cardIdWillMove = createCard("Implement Calculate Cycle Time UseCase");
        this.moveCard(workflowRepository.findById(this.workflowId).getLaneEntities().get(0).getId(), this.beginningLaneId, this.cardIdWillMove);
        this.moveCard(this.beginningLaneId, this.analysisLaneId, this.cardIdWillMove);

        DateProvider.setDate(dateFormat.parse("2020/5/23 12:00:00"));
        this.moveCard(this.analysisLaneId, this.developmentLaneId, this.cardIdWillMove);

        DateProvider.setDate(dateFormat.parse("2020/5/26 20:00:00"));
        this.moveCard(this.developmentLaneId, this.testLaneId, this.cardIdWillMove);

        DateProvider.setDate(dateFormat.parse("2020/5/27 12:00:00"));
        this.moveCard(this.testLaneId, this.readyToDeployLaneId, this.cardIdWillMove);

        DateProvider.setDate(dateFormat.parse("2020/5/27 17:00:00"));
        this.moveCard(this.readyToDeployLaneId, this.endLaneId, this.cardIdWillMove);
    }

    private String createColumn(String workflowId, String columeName){
        CreateColumnUseCase createColumnUseCase = new CreateColumnUseCase(this.workflowRepository);
        CreateColumnInput createColumnInput = new CreateColumnInput(columeName, workflowId);
        CreateColumnOutput createColumnOutput = new CreateColumnOutput();

        createColumnUseCase.execute(createColumnInput, createColumnOutput);

        return createColumnOutput.getColumnId();
    }

    private String createCard(String cardTitle){
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, domainEventBus);
        CreateCardInput createCardInput = new CreateCardInput(cardTitle, this.boardId, this.workflowId, this.beginningLaneId);
        CreateCardOutput createCardOutput = new CreateCardOutput();

        createCardUseCase.execute(createCardInput, createCardOutput);

        return createCardOutput.getCardId();
    }

    private void moveCard(String fromColumnId, String toColumnId, String cardId){
        MoveCardUseCase moveCardUseCase = new MoveCardUseCase(workflowRepository, domainEventBus);
        MoveCardInput moveCardInput = new MoveCardInput(workflowId, fromColumnId, toColumnId, cardId);
        MoveCardOutput moveCardOutput = new MoveCardOutput();

        moveCardUseCase.execute(moveCardInput, moveCardOutput);
    }
}