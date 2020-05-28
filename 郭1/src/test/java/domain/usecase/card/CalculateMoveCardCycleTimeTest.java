package domain.usecase.card;

import domain.adapters.controller.workflow.*;
import domain.adapters.repository.BoardRepositoryImpl;
import domain.adapters.repository.CardRepositoryImpl;

import domain.adapters.repository.InMemoryFlowEventRepository;
import domain.adapters.repository.WorkflowRepositoryImpl;
import domain.common.DateProvider;
import domain.entity.DomainEventBus;
import domain.entity.FlowEvent;
import domain.entity.card.Card;
import domain.entity.workflow.Workflow;
import domain.usecase.board.BoardRepository;
import domain.usecase.card.cycletime.CycleTime;
import domain.usecase.flowevent.FlowEventRepository;
import domain.usecase.stage.create.CreateStageInput;
import domain.usecase.stage.create.CreateStageOutput;
import domain.usecase.stage.create.CreateStageUseCase;
import domain.usecase.swimlane.create.CreateSwimlaneInput;
import domain.usecase.swimlane.create.CreateSwimlaneOutput;
import domain.usecase.swimlane.create.CreateSwimlaneUseCase;
import domain.usecase.workflow.WorkflowEventHandler;
import domain.usecase.workflow.WorkflowRepository;
import domain.usecase.workflow.create.CreateWorkflowInput;
import domain.usecase.workflow.create.CreateWorkflowOutput;
import domain.usecase.workflow.create.CreateWorkflowUseCase;
import org.junit.Before;
import org.junit.Test;
import util.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class CalculateMoveCardCycleTimeTest {

    private static final String EMPTY_STRING = "";
    private static final String APPLY_PAY = "Implement Apple Pay";

    private SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
    private Utilities util;
    private FlowEventRepository flowEventRepository;
    private String APPLY_PAY_ID;
    private Workflow kanbanDefaultWorkflow;

    private DomainEventBus eventBus;

    private BoardRepository boardRepository;
    private WorkflowRepository workflowRepository;
    private CardRepository cardRepository;

    @Before
    public void createThreeCards() throws ParseException {


        // record time
        DateProvider.setDate(dateFormat.parse("2020-01-01 00:00:00"));
        eventBus = new DomainEventBus();

        boardRepository = new BoardRepositoryImpl();
        workflowRepository = new WorkflowRepositoryImpl();
        cardRepository = new CardRepositoryImpl();
        flowEventRepository = new InMemoryFlowEventRepository();

        eventBus.register(new WorkflowEventHandler(boardRepository, eventBus));
        eventBus.register(new CardEventHandler(flowEventRepository, workflowRepository, eventBus));


        util = new Utilities(flowEventRepository, boardRepository, workflowRepository, cardRepository, eventBus);
        util.createBoardAndWorkflowAndStageAndSwimlane();

        // 1 board, 1 workflow, 4 stage, 4 swimlane

        // board -> workflow -> ReadyStage -> ReadySwimlane
        // -------------------> AnalysislStage -> AnalysisSwimlane
        // -------------------> DevelopmentStage -> DevelopmentSwimlane
        // -------------------> TestStage -> TestSwimlane

        util.createCardOnFirstSwimlane(new String [] {"coffee", "milk tea", "cola"});

        // card create should commit to its workflow
        // and save cardCommitted FlowEvent
        assertEquals(3, flowEventRepository.getFlowEvents().size());
        assertEquals(3, util.getReady().getCards().size());
        assertEquals(0, util.getAnalysis().getCards().size());
    }

    @Test
    public void CalculateMoveCardCycleTime() throws ParseException {

        DateProvider.setDate(dateFormat.parse("2020-01-02 01:01:02"));

        // move card from Ready to Analysis
        util.moveCard(
                util.getWorkflowId(),
                util.getReadyStageId(),
                util.getAnalysisStageId(),
                util.getReadySwimlaneId(),
                util.getAnalysisSwimlaneId(),
                util.getCardId().get(0));
        System.out.println(flowEventRepository.getFlowEvents().get(3).getOccurredOn().getTime());

        assertEquals(2, util.getReady().getCards().size());
        assertEquals(1, util.getAnalysis().getCards().size());
        assertEquals(4, flowEventRepository.getFlowEvents().size());


        CycleTime cycleTime = util.calculateCycleTime(
                util.getWorkflowId(),
                util.getReadyStageId(),
                util.getAnalysisStageId(),
                util.getReadySwimlaneId(),
                util.getAnalysisSwimlaneId(),
                util.getCardId().get(0));

        assertEquals(1, cycleTime.getDiffDays());
        assertEquals(1, cycleTime.getDiffHours());
        assertEquals(1, cycleTime.getDiffMinutes());
        assertEquals(2, cycleTime.getDiffSeconds());
    }
}
