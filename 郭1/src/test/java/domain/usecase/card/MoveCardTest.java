package domain.usecase.card;
import domain.adapters.controller.card.MoveCardInputImpl;
import domain.adapters.controller.card.MoveCardOutputImpl;
import domain.adapters.controller.workflow.*;
import domain.adapters.repository.WorkflowRepositoryImpl;
import domain.entity.DomainEventBus;
import domain.entity.card.Card;
import domain.entity.workflow.Workflow;
import domain.usecase.card.move.MoveCardInput;
import domain.usecase.card.move.MoveCardOutput;
import domain.usecase.card.move.MoveCardUseCase;
import domain.usecase.flowevent.FlowEventRepository;
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

public class MoveCardTest {
    private FlowEventRepository flowEventRepository;
    private WorkflowRepository workflowRepository ;
    private DomainEventBus eventBus;
    private String fromSwimLaneId ;
    private String toSwimLaneId ;
    private String workflowId ;
    private String fromStageId ;
    private String toStageId ;
    private Card card;
    @Before
    public void setUp() {
        workflowRepository = new WorkflowRepositoryImpl();
        eventBus = new DomainEventBus();
        DomainEventBus eventBus = new DomainEventBus() ;
        eventBus = new DomainEventBus();

        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository,eventBus);
        CreateWorkflowInput createWorkflowInput = new CreateWorkflowInputImpl();
        CreateWorkflowOutput createWorkflowOutput = new CreateWorkflowOutputImpl();

        createWorkflowInput.setWorkflowName("KanbanWorkflow");
        createWorkflowUseCase.execute(createWorkflowInput, createWorkflowOutput);

        workflowId = createWorkflowOutput.getWorkflowId();

        CreateStageUseCase createStage = new CreateStageUseCase(workflowRepository, eventBus) ;
        CreateStageInput createStageInput = new CreateStageInputImpl() ;
        CreateStageOutput createStageOutput = new CreateStageOutputImpl() ;

        createStageInput.setStageName("eat");
        createStageInput.setWorkflowId(workflowId);
        createStage.execute( createStageInput, createStageOutput ) ;

        fromStageId = createStageOutput.getStageId();
        toStageId = createStageOutput.getStageId();


        CreateSwimlaneUseCase createSwimlaneUseCase = new CreateSwimlaneUseCase(workflowRepository, eventBus);
        CreateSwimlaneInput createSwimlaneInput = new CreateSwimlaneInputImpl();
        CreateSwimlaneOutput createSwimlaneOutput = new CreateSwimlaneOutputImpl();

        createSwimlaneInput.setName("InProgress");
        createSwimlaneInput.setStageId(fromStageId);
        createSwimlaneInput.setWorkflowId(workflowId);

        createSwimlaneUseCase.execute(createSwimlaneInput,createSwimlaneOutput);

        fromSwimLaneId = createSwimlaneOutput.getSwimlaneId() ;

        createSwimlaneInput.setName("Done");
        createSwimlaneInput.setStageId(toStageId);
        createSwimlaneUseCase.execute(createSwimlaneInput,createSwimlaneOutput);
        toSwimLaneId = createSwimlaneOutput.getSwimlaneId() ;

        card = new Card() ;
        card.setName( "eatBreakfast" );
        Workflow workflow =  workflowRepository.getWorkFlowById(workflowId);
        workflow.getStageById(fromStageId).getSwimlaneById(fromSwimLaneId).addCard(card.getId());
        workflowRepository.save(workflow);

    }

    @Test
    public void Card_should_be_moved_from_todoStage_to_doingStage() {
        MoveCardInput moveCardUseCaseInput = new MoveCardInputImpl();
        MoveCardOutput moveCardUseCaseOutput = new MoveCardOutputImpl();
        MoveCardUseCase moveCardUseCase = new MoveCardUseCase(flowEventRepository, workflowRepository, eventBus);

        moveCardUseCaseInput.setWorkflowId(workflowId);
        moveCardUseCaseInput.setFromStageId(fromStageId);
        moveCardUseCaseInput.setToStageId(toStageId);
        moveCardUseCaseInput.setFromSwimlaneId(fromSwimLaneId);
        moveCardUseCaseInput.setToSwimlaneId(toSwimLaneId);
        moveCardUseCaseInput.setCardId(card.getId());

        moveCardUseCase.execute(moveCardUseCaseInput, moveCardUseCaseOutput);

        assertEquals(0, workflowRepository.getWorkFlowById(workflowId).getStageById(fromStageId).getSwimlaneById(fromSwimLaneId).getCards().size());
        assertEquals(1, workflowRepository.getWorkFlowById(workflowId).getStageById(toStageId).getSwimlaneById(toSwimLaneId).getCards().size());
        assertEquals(card.getId(), workflowRepository.getWorkFlowById(workflowId).getStageById(toStageId).getSwimlaneById(toSwimLaneId).getCards().get(0));

    }

    @Test
    public void Card_should_be_moved_from_doingStage_to_doneStage() {

    }

    @Test
    public void Card_should_be_moved_from_doneStage_to_todoStage() {

    }
}
