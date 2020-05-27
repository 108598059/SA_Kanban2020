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

public class CalculateCycleTimeTest {

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
        DateProvider.setDate(dateFormat.parse("2019-03-01 00 00:00"));
        eventBus = new DomainEventBus();

        boardRepository = new BoardRepositoryImpl();
        workflowRepository = new WorkflowRepositoryImpl();
        cardRepository = new CardRepositoryImpl();
        flowEventRepository = new InMemoryFlowEventRepository();
        eventBus.register(new WorkflowEventHandler(boardRepository, eventBus));
        eventBus.register(new CardEventHandler(flowEventRepository, workflowRepository, eventBus));



        util = new Utilities(flowEventRepository, boardRepository, workflowRepository, cardRepository, eventBus);
        util.createWorkflowAndStageAndSwimlane();


        util.createCardOnSwimlane(new String [] {"apple pay", "Line pay", "Pay by VISA"});

//        assertEquals(3, flowEventRepository.getFlowEvents().size());
//        assertEquals(3, util.getReady().getCards().size());
//        assertEquals(0, util.getAnalysis().getCards().size());
        System.out.println(flowEventRepository.getFlowEvents().toString());
    }

    @Test
    public void b() throws ParseException {
        DateProvider.setDate(dateFormat.parse("2020-05-27 23:00:00"));
        System.out.println(util.getCardId().get(0));
        util.moveCard(util.getWorkflowId(),
                util.getReadyStageId(),
                util.getAnalysisStageId(),
                util.getReadySwimlaneId(),
                util.getAnalysisSwimlaneId(),
                util.getCardId().get(0));

        assertEquals(2, util.getReady().getCards().size());
        assertEquals(1, util.getAnalysis().getCards().size());

        assertEquals(4, flowEventRepository.getFlowEvents().size());


//
//        DateProvider.setDate(dateFormat.parse("2019-03-05 00:00:00"));
//        moveCard(APPLY_PAY_ID, kanbanDefaultWorkflow, util.getAnalysis(), util.getDevelopment());
//
//
//
//        DateProvider.setDate(dateFormat.parse("2019-03-07 00:00:00"));
//        moveCard(APPLY_PAY_ID, kanbanDefaultWorkflow, util.getDevelopment(), util.getTest());
//
//
//
//        DateProvider.setDate(dateFormat.parse("2019-03-10 00:00:00"));
//        moveCard(APPLY_PAY_ID, kanbanDefaultWorkflow, util.getTest(), util.getReadyToDeploy());
//
//
//        DateProvider.setDate(dateFormat.parse("2019-03-20 00:00:00"));
//        moveCard(APPLY_PAY_ID, kanbanDefaultWorkflow, util.getReadyToDeploy(), util.getDeployed());
//
//
//
//        // move card backward to see what happens
//        DateProvider.setDate(dateFormat.parse("2019-03-20 01:00:00"));
//        moveCard(APPLY_PAY_ID, kanbanDefaultWorkflow, util.getDeployed(), util.getReadyToDeploy());
//
//        DateProvider.setDate(dateFormat.parse("2019-03-20 01:03:00"));
//        moveCard(APPLY_PAY_ID, kanbanDefaultWorkflow, util.getReadyToDeploy(), util.getDeployed());
//
//        DateProvider.setDate(dateFormat.parse("2019-03-28 00:00:00"));
//        Card card = util.getCardRepository().findFirstByName(APPLY_PAY);
//        CycleTimeCalculator cycleTimeCalculator = new CycleTimeCalculator(flowEventRepository);
//        List<FlowEntryPair> flowEntryPairs = cycleTimeCalculator.getCycleTime(card.getId());
//
//        System.out.println();
//        System.out.println("Card : [" + card.getName() + "]");
//        for (FlowEntryPair each : flowEntryPairs){
//            Lane lane = kanbanDefaultWorkflow.findLaneById(each.getLaneId());
//            System.out.print("[" + lane.getName() + "] ");
//            System.out.println(each.getCycleTime().toString());
//        }
    }
}
