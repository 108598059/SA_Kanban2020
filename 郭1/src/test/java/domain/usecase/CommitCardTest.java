package domain.usecase;

import domain.adapters.repository.CardRepositoryImpl;
import domain.adapters.repository.WorkflowRepositoryImpl;
import domain.adapters.controller.card.CommitCardInputImpl;
import domain.adapters.controller.card.CommitCardOutputImpl;
import domain.adapters.controller.card.CreateCardInputImpl;
import domain.adapters.controller.card.CreateCardOutputImpl;
import domain.adapters.controller.workflow.*;
import domain.entity.DomainEventBus;
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
import domain.usecase.workflow.commit.CommitCardInput;
import domain.usecase.workflow.commit.CommitCardOutput;
import domain.usecase.workflow.commit.CommitCardUseCase;
import domain.usecase.workflow.create.CreateWorkflowInput;
import domain.usecase.workflow.create.CreateWorkflowOutput;
import domain.usecase.workflow.create.CreateWorkflowUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CommitCardTest {
    private String workflowId;
    private String stageId;
    private String swimlaneId;
    private String cardId;

    private WorkflowRepository workflowRepository;
    private CardRepository cardRepository;
    private DomainEventBus eventBus;

    @Before
    public void setUp(){
        eventBus = new DomainEventBus();

        workflowRepository = new WorkflowRepositoryImpl();
        cardRepository = new CardRepositoryImpl();

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

        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository,eventBus);
        CreateCardInput createCardInput = new CreateCardInputImpl();
        CreateCardOutput createCardOutput = new CreateCardOutputImpl();


        createCardInput.setCardName( "card1" ) ;
        createCardInput.setWorkflowId(workflowId);
        createCardInput.setStageId(stageId);
        createCardInput.setSwimlaneId(swimlaneId);

        createCardUseCase.execute( createCardInput, createCardOutput ) ;
        cardId = createCardOutput.getCardId();
    }

    @Test
    public void CommitCardTest() {

        CommitCardInput commitCardInput = new CommitCardInputImpl();
        CommitCardOutput commitCardOutput = new CommitCardOutputImpl();
        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(workflowRepository);

        commitCardInput.setWorkflowId(workflowId);
        commitCardInput.setStageId(stageId);
        commitCardInput.setSwimlaneId(swimlaneId);
        commitCardInput.setCardId(cardId);


        commitCardUseCase.execute(commitCardInput,commitCardOutput);

        assertEquals(cardId,commitCardOutput.getCardId());
    }
}
