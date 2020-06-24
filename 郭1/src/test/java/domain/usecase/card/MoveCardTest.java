package domain.usecase.card;
import domain.adapters.controller.card.input.MoveCardInputImpl;
import domain.adapters.controller.card.output.MoveCardOutputImpl;
import domain.adapters.controller.workflow.input.CreateStageInputImpl;
import domain.adapters.controller.workflow.input.CreateSwimlaneInputImpl;
import domain.adapters.controller.workflow.input.CreateWorkflowInputImpl;
import domain.adapters.controller.workflow.output.CreateStageOutputImpl;
import domain.adapters.controller.workflow.output.CreateSwimlaneOutputImpl;
import domain.adapters.controller.workflow.output.CreateWorkflowOutputImpl;
import domain.adapters.repository.InMemoryFlowEventRepository;
import domain.adapters.repository.WorkflowRepositoryImpl;
import domain.entity.DomainEventBus;
import domain.entity.aggregate.card.Card;
import domain.entity.aggregate.workflow.Workflow;
import domain.usecase.card.move.MoveCardInput;
import domain.usecase.card.move.MoveCardOutput;
import domain.usecase.card.move.MoveCardUseCase;
import domain.usecase.flowevent.FlowEventRepository;
import domain.usecase.workflow.WorkflowDTO;
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
        Workflow workflow = WorkflowTransformer.toWorkflow(workflowRepository.getWorkFlowById(workflowId));
        workflow.getStageById(fromStageId).getSwimlaneById(fromSwimLaneId).addCard(card.getId());
        workflowRepository.save(WorkflowTransformer.toDTO(workflow));

        flowEventRepository = new InMemoryFlowEventRepository() ;

    }

    @Test
    public void Card_should_be_moved_from_todoStage_to_doingStage() {
        MoveCardInput moveCardUseCaseInput = new MoveCardInputImpl();
        MoveCardOutput moveCardUseCaseOutput = new MoveCardOutputImpl();
        MoveCardUseCase moveCardUseCase = new MoveCardUseCase(workflowRepository, eventBus);

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

}
