package ddd.kanban.usecase.report.cycleTime;

import ddd.kanban.adapter.repository.board.InMemoryBoardRepository;
import ddd.kanban.adapter.repository.card.InMemoryCardRepository;
import ddd.kanban.adapter.repository.flowevent.InMemoryFlowEventRepository;
import ddd.kanban.adapter.repository.workflow.InMemoryWorkflowRepository;
import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.common.DateProvider;
import ddd.kanban.domain.model.kanbanboard.workflow.Workflow;
import ddd.kanban.usecase.HierarchyInitial;
import ddd.kanban.usecase.card.create.CreateCardInput;
import ddd.kanban.usecase.card.create.CreateCardOutput;
import ddd.kanban.usecase.card.create.CreateCardUseCase;
import ddd.kanban.usecase.domainevent.handler.DomainEventHandler;
import ddd.kanban.usecase.domainevent.handler.FlowEventHandler;
import ddd.kanban.usecase.repository.BoardRepository;
import ddd.kanban.usecase.repository.CardRepository;
import ddd.kanban.usecase.repository.FlowEventRepository;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.kanbanboard.workflow.create.CreateColumnInput;
import ddd.kanban.usecase.kanbanboard.workflow.create.CreateColumnOutput;
import ddd.kanban.usecase.kanbanboard.workflow.create.CreateColumnUseCase;
import ddd.kanban.usecase.kanbanboard.workflow.mapper.WorkflowEntityMapper;
import ddd.kanban.usecase.card.move.MoveCardInput;
import ddd.kanban.usecase.card.move.MoveCardOutput;
import ddd.kanban.usecase.card.move.MoveCardUseCase;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class CalculateCycleTimeUseCaseTest {
    private HierarchyInitial hierarchyInitial;

    private String boardId;
    private String workflowId;

    private String defaultColumnId;
    private String beginningColumnId;
    private String analysisColumnId;
    private String developmentColumnId;
    private String testColumnId;
    private String readyToDeployColumnId;
    private String endColumnId;

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
        workflowRepository = new InMemoryWorkflowRepository();
        boardRepository = new InMemoryBoardRepository();
        flowEventRepository = new InMemoryFlowEventRepository();

        domainEventBus = new DomainEventBus();
        domainEventBus.register(new DomainEventHandler(workflowRepository, boardRepository, domainEventBus));
        domainEventBus.register(new FlowEventHandler(flowEventRepository));

        hierarchyInitial = new HierarchyInitial(boardRepository, workflowRepository, domainEventBus);

        boardId = hierarchyInitial.CreateBoard();

        Workflow workflow = WorkflowEntityMapper.mappingWorkflowFrom(workflowRepository.findAll().get(0));
        workflowId = workflow.getId();
        defaultColumnId = workflow.getColumns().get(0).getId();
        createKanbanBoardTemplateColumns();
    }

    @Test
    public void testCalculateCycleTimeUseCaseForSingleColumn() throws ParseException {
        DateProvider.setDate(dateFormat.parse("2020/5/20 00:00:00"));
        String cardId = createCard("Implement Calculate Cycle Time UseCase");
        moveCard(defaultColumnId, beginningColumnId, cardId);

        DateProvider.setDate(dateFormat.parse("2020/5/23 00:00:00"));
        moveCard(beginningColumnId, analysisColumnId, cardId);

        CalculateCycleTimeUseCase calculateCycleTimeUseCase = new CalculateCycleTimeUseCase(workflowRepository, flowEventRepository, domainEventBus);
        CalculateCycleTimeInput calculateCycleTimeInput = new CalculateCycleTimeInput(cardId, workflowId, beginningColumnId, beginningColumnId);
        CalculateCycleTimeOutput calculateCycleTimeOutput = new CalculateCycleTimeOutput();

        calculateCycleTimeUseCase.execute(calculateCycleTimeInput, calculateCycleTimeOutput);

        assertEquals(3, calculateCycleTimeOutput.getCycleTime().getDay());
    }

    @Test
    public void testCalculateCycleTimeUseCaseForCardWithoutMove() throws ParseException {
        DateProvider.setDate(dateFormat.parse("2020/5/20 00:00:00"));
        String cardId = createCard("Buy house");

        DateProvider.setDate(dateFormat.parse("2020/6/20 00:00:00"));

        CalculateCycleTimeUseCase calculateCycleTimeUseCase = new CalculateCycleTimeUseCase(workflowRepository, flowEventRepository, domainEventBus);
        CalculateCycleTimeInput calculateCycleTimeInput = new CalculateCycleTimeInput(cardId, workflowId, defaultColumnId, defaultColumnId);
        CalculateCycleTimeOutput calculateCycleTimeOutput = new CalculateCycleTimeOutput();

        calculateCycleTimeUseCase.execute(calculateCycleTimeInput, calculateCycleTimeOutput);

        assertEquals(31, calculateCycleTimeOutput.getCycleTime().getDay());
    }

    @Test
    public void testCalculateCycleTimeUseCaseForMoveCardFromReadyToDeploy() throws ParseException {
        DateProvider.setDate(dateFormat.parse("2020/5/22 00:00:00"));
        String cardId = createCard("Implement Calculate Cycle Time UseCase");
        moveCard(defaultColumnId, beginningColumnId, cardId);
        moveCard(beginningColumnId, analysisColumnId, cardId);

        DateProvider.setDate(dateFormat.parse("2020/5/23 12:00:00"));
        moveCard(analysisColumnId, developmentColumnId, cardId);

        DateProvider.setDate(dateFormat.parse("2020/5/26 20:00:00"));
        moveCard(developmentColumnId, testColumnId, cardId);

        DateProvider.setDate(dateFormat.parse("2020/5/27 12:00:00"));
        moveCard(testColumnId, readyToDeployColumnId, cardId);

        DateProvider.setDate(dateFormat.parse("2020/5/27 17:00:00"));
        moveCard(readyToDeployColumnId, endColumnId, cardId);

        CalculateCycleTimeUseCase calculateCycleTimeUseCase = new CalculateCycleTimeUseCase(workflowRepository, flowEventRepository, domainEventBus);
        CalculateCycleTimeInput calculateCycleTimeInput = new CalculateCycleTimeInput(cardId, workflowId, beginningColumnId, readyToDeployColumnId);
        CalculateCycleTimeOutput calculateCycleTimeOutput = new CalculateCycleTimeOutput();

        calculateCycleTimeUseCase.execute(calculateCycleTimeInput, calculateCycleTimeOutput);

        assertEquals(5, calculateCycleTimeOutput.getCycleTime().getDay());
    }

    private String createColumn(String workflowId, String columnName){
        CreateColumnUseCase createColumnUseCase = new CreateColumnUseCase(workflowRepository, domainEventBus);
        CreateColumnInput createColumnInput = new CreateColumnInput(columnName, workflowId);
        CreateColumnOutput createColumnOutput = new CreateColumnOutput();

        createColumnUseCase.execute(createColumnInput, createColumnOutput);

        return createColumnOutput.getColumnId();
    }

    private String createCard(String cardTitle){
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository, domainEventBus);
        CreateCardInput createCardInput = new CreateCardInput(cardTitle, boardId, workflowId, defaultColumnId);
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

    private void createKanbanBoardTemplateColumns(){
        beginningColumnId = createColumn(workflowId, "Ready");
        analysisColumnId = createColumn(workflowId, "Analysis");
        developmentColumnId = createColumn(workflowId, "Development");
        testColumnId = createColumn(workflowId, "Test");
        readyToDeployColumnId = createColumn(workflowId, "Ready to Deploy");
        endColumnId = createColumn(workflowId, "Deployed");
    }
}
