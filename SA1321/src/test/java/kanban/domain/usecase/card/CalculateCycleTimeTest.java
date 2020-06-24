package kanban.domain.usecase.card;

import kanban.domain.Utility;
import kanban.domain.adapter.presenter.card.cycleTime.CalculateCycleTimePresenter;
import kanban.domain.adapter.repository.board.InMemoryBoardRepository;
import kanban.domain.adapter.repository.card.InMemoryCardRepository;
import kanban.domain.adapter.repository.domainEvent.InMemoryDomainEventRepository;
import kanban.domain.adapter.repository.flowEvent.InMemoryFlowEventRepository;
import kanban.domain.adapter.repository.workflow.InMemoryWorkflowRepository;
import kanban.domain.model.DomainEventBus;
import kanban.domain.model.common.DateProvider;
import kanban.domain.usecase.board.IBoardRepository;
import kanban.domain.usecase.card.cycleTime.CalculateCycleTimeInput;
import kanban.domain.usecase.card.cycleTime.CalculateCycleTimeOutput;
import kanban.domain.usecase.card.cycleTime.CalculateCycleTimeUseCase;
import kanban.domain.usecase.card.cycleTime.CycleTimeModel;
import kanban.domain.usecase.domainEvent.IDomainEventRepository;
import kanban.domain.usecase.flowEvent.IFlowEventRepository;
import kanban.domain.usecase.handler.card.CardEventHandler;
import kanban.domain.usecase.handler.domainEvent.DomainEventHandler;
import kanban.domain.usecase.handler.flowEvent.FlowEventHandler;
import kanban.domain.usecase.handler.workflow.WorkflowEventHandler;
import kanban.domain.usecase.workflow.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class CalculateCycleTimeTest {

    private String cardId;

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private IDomainEventRepository domainEventRepository;
    private IFlowEventRepository flowEventRepository;
    private ICardRepository cardRepository;

    private DomainEventBus eventBus;
    private Utility utility;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


    @Before
    public void setUp() throws Exception {
        boardRepository = new InMemoryBoardRepository();
        workflowRepository = new InMemoryWorkflowRepository();
        domainEventRepository = new InMemoryDomainEventRepository();
        cardRepository = new InMemoryCardRepository();
        flowEventRepository = new InMemoryFlowEventRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new WorkflowEventHandler(boardRepository, eventBus));
        eventBus.register(new CardEventHandler(boardRepository, workflowRepository, eventBus));
        eventBus.register(new DomainEventHandler(domainEventRepository));
        eventBus.register(new FlowEventHandler(flowEventRepository));

        DateProvider.setDate(dateFormat.parse("2020/5/18 09:00:00"));

        utility = new Utility(boardRepository, workflowRepository, flowEventRepository, cardRepository, eventBus);
        utility.createDefaultKanbanBoard();
        cardId = utility.createCardInDefaultKanbanBoard("First");
    }

    //committed card date 2020/5/18 09:00:00
    //calculate cycleTime date 2020/5/25 08:10:20
    @Test
    public void Calculate_CycleTime_Should_Be_Correct_Without_Moving_Card() throws ParseException {

        DateProvider.setDate(dateFormat.parse("2020/5/25 08:10:20"));

        CalculateCycleTimeUseCase calculateCycleTimeUseCase = new CalculateCycleTimeUseCase(workflowRepository, flowEventRepository, eventBus);
        CalculateCycleTimeInput input = calculateCycleTimeUseCase;
        input.setWorkflowId(utility.getDefaultWorkflowId());
        input.setCardId(cardId);
        input.setBeginningStageId(utility.getReadyStageId());
        input.setEndingStageId(utility.getDeployedStageId());

        CalculateCycleTimeOutput output = new CalculateCycleTimePresenter();

        calculateCycleTimeUseCase.execute(input, output);

        assertEquals(6, output.getCycleTimeModel().getDiffDays());
        assertEquals(23, output.getCycleTimeModel().getDiffHours());
        assertEquals(10, output.getCycleTimeModel().getDiffMinutes());
        assertEquals(20, output.getCycleTimeModel().getDiffSeconds());
    }

    //committed card date 2020/5/18 09:00:00
    //move card date 2020/5/20 10:00:00
    //calculate cycleTime date "2020/5/24 10:00:00"
    @Test
    public void Calculate_CycleTime_Should_Be_Correct() throws ParseException {
        DateProvider.setDate(dateFormat.parse("2020/5/20 10:00:00"));
        utility.moveCard(utility.getDefaultWorkflowId(), cardId, utility.getReadyStageId(), utility.getAnalysisStageId());

        DateProvider.setDate(dateFormat.parse("2020/5/24 10:00:00"));
        CalculateCycleTimeUseCase calculateCycleTimeUseCase = new CalculateCycleTimeUseCase(workflowRepository, flowEventRepository, eventBus);
        CalculateCycleTimeInput input = calculateCycleTimeUseCase;
        input.setWorkflowId(utility.getDefaultWorkflowId());
        input.setCardId(cardId);
        input.setBeginningStageId(utility.getReadyStageId());
        input.setEndingStageId(utility.getDeployedStageId());

        CalculateCycleTimeOutput output = new CalculateCycleTimePresenter();

        calculateCycleTimeUseCase.execute(input, output);

        assertEquals(6, output.getCycleTimeModel().getDiffDays());
        assertEquals(1, output.getCycleTimeModel().getDiffHours());
        assertEquals(0, output.getCycleTimeModel().getDiffMinutes());
        assertEquals(0, output.getCycleTimeModel().getDiffSeconds());
    }

    @Test
    public void Calculate_CycleTime_Should_Be_Correct_With_Moving_Card_From_Ready_To_ReadyToDeploy() throws ParseException {
        DateProvider.setDate(dateFormat.parse("2020/5/19 09:00:00"));
        utility.moveCard(utility.getDefaultWorkflowId(), cardId, utility.getReadyStageId(), utility.getAnalysisStageId());
        CycleTimeModel cycleTimeModel = utility.calculateCycleTime(utility.getDefaultWorkflowId(), cardId, utility.getReadyStageId(), utility.getAnalysisStageId());
        assertEquals(1, cycleTimeModel.getDiffDays());
        assertEquals(0, cycleTimeModel.getDiffHours());
        assertEquals(0, cycleTimeModel.getDiffMinutes());
        assertEquals(0, cycleTimeModel.getDiffSeconds());

        DateProvider.setDate(dateFormat.parse("2020/5/22 11:00:00"));
        utility.moveCard(utility.getDefaultWorkflowId(), cardId, utility.getAnalysisStageId(), utility.getDevelopmentStageId());

        cycleTimeModel = utility.calculateCycleTime(utility.getDefaultWorkflowId(), cardId, utility.getReadyStageId(), utility.getDevelopmentStageId());
        assertEquals(4, cycleTimeModel.getDiffDays());
        assertEquals(2, cycleTimeModel.getDiffHours());
        assertEquals(0, cycleTimeModel.getDiffMinutes());
        assertEquals(0, cycleTimeModel.getDiffSeconds());

        DateProvider.setDate(dateFormat.parse("2020/5/23 12:00:00"));
        utility.moveCard(utility.getDefaultWorkflowId(), cardId, utility.getDevelopmentStageId(), utility.getTestStageId());
        cycleTimeModel = utility.calculateCycleTime(utility.getDefaultWorkflowId(), cardId, utility.getReadyStageId(), utility.getTestStageId());
        assertEquals(5, cycleTimeModel.getDiffDays());
        assertEquals(3, cycleTimeModel.getDiffHours());
        assertEquals(0, cycleTimeModel.getDiffMinutes());
        assertEquals(0, cycleTimeModel.getDiffSeconds());
        //---------------------------- Move card backward

        DateProvider.setDate(dateFormat.parse("2020/5/24 13:30:30"));
        utility.moveCard(utility.getDefaultWorkflowId(), cardId, utility.getTestStageId(), utility.getDevelopmentStageId());
        cycleTimeModel = utility.calculateCycleTime(utility.getDefaultWorkflowId(), cardId, utility.getReadyStageId(), utility.getTestStageId());
        assertEquals(6, cycleTimeModel.getDiffDays());
        assertEquals(4, cycleTimeModel.getDiffHours());
        assertEquals(30, cycleTimeModel.getDiffMinutes());
        assertEquals(30, cycleTimeModel.getDiffSeconds());

        //---------------------------- Move card forward
        DateProvider.setDate(dateFormat.parse("2020/5/25 14:25:50"));
        utility.moveCard(utility.getDefaultWorkflowId(), cardId, utility.getDevelopmentStageId(), utility.getTestStageId());
        cycleTimeModel = utility.calculateCycleTime(utility.getDefaultWorkflowId(), cardId, utility.getReadyStageId(), utility.getTestStageId());
        assertEquals(7, cycleTimeModel.getDiffDays());
        assertEquals(5, cycleTimeModel.getDiffHours());
        assertEquals(25, cycleTimeModel.getDiffMinutes());
        assertEquals(50, cycleTimeModel.getDiffSeconds());

        DateProvider.setDate(dateFormat.parse("2020/5/26 18:10:15"));
        utility.moveCard(utility.getDefaultWorkflowId(), cardId, utility.getTestStageId(), utility.getReadyToDeployStageId());
        cycleTimeModel = utility.calculateCycleTime(utility.getDefaultWorkflowId(), cardId, utility.getReadyStageId(), utility.getReadyToDeployStageId());
        assertEquals(8, cycleTimeModel.getDiffDays());
        assertEquals(9, cycleTimeModel.getDiffHours());
        assertEquals(10, cycleTimeModel.getDiffMinutes());
        assertEquals(15, cycleTimeModel.getDiffSeconds());
    }
}
