package domain.usecase.card;

import domain.adapters.controller.workflow.input.CreateStageInputImpl;
import domain.adapters.controller.workflow.input.CreateSwimlaneInputImpl;
import domain.adapters.controller.workflow.input.CreateWorkflowInputImpl;
import domain.adapters.controller.workflow.output.CreateStageOutputImpl;
import domain.adapters.controller.workflow.output.CreateSwimlaneOutputImpl;
import domain.adapters.controller.workflow.output.CreateWorkflowOutputImpl;
import domain.adapters.repository.CardRepositoryImpl;
import domain.adapters.repository.InMemoryFlowEventRepository;
import domain.adapters.repository.WorkflowRepositoryImpl;
import domain.adapters.controller.card.input.CreateCardInputImpl;
import domain.adapters.controller.card.output.CreateCardOutputImpl;
import domain.entity.DomainEventBus;

import domain.entity.aggregate.card.Card;
import domain.entity.aggregate.workflow.Workflow;
import domain.usecase.card.create.CreateCardInput;
import domain.usecase.card.create.CreateCardOutput;
import domain.usecase.card.create.CreateCardUseCase;

import domain.usecase.flowevent.FlowEventRepository;
import domain.usecase.workflow.WorkflowTransformer;
import domain.usecase.workflow.create.CreateStageInput;
import domain.usecase.workflow.create.CreateStageOutput;
import domain.usecase.workflow.create.CreateStageUseCase;
import domain.usecase.workflow.create.CreateSwimlaneInput;
import domain.usecase.workflow.create.CreateSwimlaneOutput;
import domain.usecase.workflow.create.CreateSwimlaneUseCase;
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

    private FlowEventRepository flowEventRepository;
    private WorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    @Before
    public void setUp(){
        eventBus = new DomainEventBus();
        flowEventRepository = new InMemoryFlowEventRepository();
        workflowRepository = new WorkflowRepositoryImpl();

        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository,eventBus);
        CreateWorkflowInput createWorkflowInput = new CreateWorkflowInputImpl();
        CreateWorkflowOutput createWorkflowOutput = new CreateWorkflowOutputImpl();

        createWorkflowInput.setWorkflowName("workflow1");

        createWorkflowUseCase.execute(createWorkflowInput, createWorkflowOutput);
        workflowId = createWorkflowOutput.getWorkflowId();

        CreateStageUseCase createStage = new CreateStageUseCase(workflowRepository, eventBus) ;
        CreateStageInput createStageInput = new CreateStageInputImpl() ;
        CreateStageOutput createStageOutput = new CreateStageOutputImpl() ;

        createStageInput.setStageName("backlog");
        createStageInput.setWorkflowId(workflowId);

        createStage.execute( createStageInput, createStageOutput ) ;

        stageId = createStageOutput.getStageId();

        CreateSwimlaneUseCase createSwimlaneUseCase = new CreateSwimlaneUseCase(workflowRepository, eventBus);
        CreateSwimlaneInput createSwimlaneInput = new CreateSwimlaneInputImpl();
        CreateSwimlaneOutput createSwimlaneOutput = new CreateSwimlaneOutputImpl();

        createSwimlaneInput.setName("Ready");
        createSwimlaneInput.setStageId(stageId);
        createSwimlaneInput.setWorkflowId(workflowId);

        createSwimlaneUseCase.execute(createSwimlaneInput,createSwimlaneOutput);

        swimlaneId = createSwimlaneOutput.getSwimlaneId();

        eventBus.register(new CardEventHandler(flowEventRepository, workflowRepository, eventBus));

    }

    @Test
    public void Create_Card_should_commit_its_workflow() {

        CardRepository cardRepository = new CardRepositoryImpl();
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository,eventBus);
        CreateCardInput createCardInput = new CreateCardInputImpl();
        CreateCardOutput createCardOutput = new CreateCardOutputImpl();


        createCardInput.setCardName( "card1" ) ;
        createCardInput.setWorkflowId(workflowId);
        createCardInput.setStageId(stageId);
        createCardInput.setSwimlaneId(swimlaneId);

        createCardUseCase.execute( createCardInput, createCardOutput ) ;

        Card card = CardTransformer.toCard(cardRepository.getCardById(createCardOutput.getCardId()));
        assertEquals("card1", card.getName());

        Workflow workflow = WorkflowTransformer.toWorkflow(workflowRepository.getWorkFlowById(workflowId));
        assertEquals(1, workflow.getStageById(stageId).getSwimlaneById(swimlaneId).getCards().size());
        assertEquals(createCardOutput.getCardId(), workflow.getStageById(stageId).getSwimlaneById(swimlaneId).getCards().get(0));

    }
}
