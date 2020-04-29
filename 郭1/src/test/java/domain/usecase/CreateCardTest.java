package domain.usecase;

import domain.adapter.CardRepositoryImpl;
import domain.adapter.WorkflowRepositoryImpl;
import domain.controller.*;
import domain.entity.DomainEventBus;
import domain.usecase.board.BoardRepository;

import domain.usecase.card.CardEventHandler;
import domain.usecase.card.CardRepository;
import domain.usecase.card.create.CreateCardInput;
import domain.usecase.card.create.CreateCardOutput;
import domain.usecase.card.create.CreateCardUseCase;

import domain.usecase.stage.create.CreateStageInput;
import domain.usecase.stage.create.CreateStageOutput;
import domain.usecase.stage.create.CreateStageUseCase;
import domain.usecase.swimlane.create.CreateSwimlaneInput;
import domain.usecase.swimlane.create.CreateSwimlaneOutput;
import domain.usecase.swimlane.create.CreateSwimlaneUseCase;
import domain.usecase.workflow.WorkflowRepository;
import domain.usecase.workflow.create.CreateWorkflowInput;
import domain.usecase.workflow.create.CreateWorkflowOutput;
import domain.usecase.workflow.create.CreateWorkflowUseCase;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateCardTest {

    private String workflowId;
    private String stageId;
    private String swimlaneId;


    private BoardRepository boardRepository;
    private WorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    @Before
    public void setUp(){
        eventBus = new DomainEventBus();

        workflowRepository = new WorkflowRepositoryImpl();

        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository,eventBus);
        CreateWorkflowInput createWorkflowInput = new CreateWorkflowInputImpl();
        CreateWorkflowOutput createWorkflowOutput = new CreateWorkflowOutputImpl();

        createWorkflowInput.setWorkflowName("workflow1");

        createWorkflowUseCase.execute(createWorkflowInput, createWorkflowOutput);
        workflowId = createWorkflowOutput.getWorkflowId();

        CreateStageUseCase createStage = new CreateStageUseCase(workflowRepository) ;
        CreateStageInput createStageInput = new CreateStageInputImpl() ;
        CreateStageOutput createStageOutput = new CreateStageOutputImpl() ;

        createStageInput.setStageName("backlog");
        createStageInput.setWorkflowId(workflowId);

        createStage.execute( createStageInput, createStageOutput ) ;

        stageId = createStageOutput.getStageId();

        CreateSwimlaneUseCase createSwimlaneUseCase = new CreateSwimlaneUseCase(workflowRepository);
        CreateSwimlaneInput createSwimlaneInput = new CreateSwimlaneInputImpl();
        CreateSwimlaneOutput createSwimlaneOutput = new CreateSwimlaneOutputImpl();

        createSwimlaneInput.setName("Ready");
        createSwimlaneInput.setStageId(stageId);
        createSwimlaneInput.setWorkflowId(workflowId);

        createSwimlaneUseCase.execute(createSwimlaneInput,createSwimlaneOutput);

        swimlaneId = createSwimlaneOutput.getSwimlaneId();

        eventBus.register(new CardEventHandler(workflowRepository));

    }

    @Test
    public void CreateCard() {

        CardRepository cardRepository = new CardRepositoryImpl();
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository,eventBus);
        CreateCardInput CreateCardInput = new CreateCardInputImpl();
        CreateCardOutput CreateCardOutput = new CreateCardOutputImpl();


        CreateCardInput.setCardName( "card1" ) ;
        CreateCardInput.setWorkflowId(workflowId);
        CreateCardInput.setStageId(stageId);
        CreateCardInput.setSwimlaneId(swimlaneId);

        createCardUseCase.execute( CreateCardInput, CreateCardOutput ) ;

        assertNotNull(CreateCardOutput.getCardId());
        assertEquals(true, workflowRepository.getWorkFlowById(workflowId).isCardExist(CreateCardOutput.getCardId()));

    }
}
