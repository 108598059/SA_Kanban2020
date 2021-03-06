package domain.usecase.workflow;

import domain.adapters.controller.workflow.input.CreateStageInputImpl;
import domain.adapters.controller.workflow.input.CreateSwimlaneInputImpl;
import domain.adapters.controller.workflow.input.CreateWorkflowInputImpl;
import domain.adapters.controller.workflow.output.CreateStageOutputImpl;
import domain.adapters.controller.workflow.output.CreateSwimlaneOutputImpl;
import domain.adapters.controller.workflow.output.CreateWorkflowOutputImpl;
import domain.adapters.repository.WorkflowRepositoryImpl;
import domain.entity.DomainEventBus;
import domain.entity.aggregate.workflow.Workflow;
import domain.usecase.workflow.create.CreateStageInput;
import domain.usecase.workflow.create.CreateStageOutput;
import domain.usecase.workflow.create.CreateStageUseCase;
import domain.usecase.workflow.create.CreateSwimlaneInput;
import domain.usecase.workflow.create.CreateSwimlaneOutput;
import domain.usecase.workflow.create.CreateSwimlaneUseCase;
import domain.usecase.workflow.create.CreateWorkflowInput;
import domain.usecase.workflow.create.CreateWorkflowOutput;
import domain.usecase.workflow.create.CreateWorkflowUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateSwimlaneTest {
    private WorkflowRepositoryImpl workflowRepository;
    private String workflowId;
    private String stageId;
    private DomainEventBus eventBus;

    @Before
    public void setUp(){

        workflowRepository = new WorkflowRepositoryImpl();
        eventBus = new DomainEventBus();

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

    }
    @Test
    public void createSwimlaneTest() {

        CreateSwimlaneUseCase createSwimlaneUseCase = new CreateSwimlaneUseCase(workflowRepository, eventBus);
        CreateSwimlaneInput createSwimlaneInput = new CreateSwimlaneInputImpl();
        CreateSwimlaneOutput createSwimlaneOutput = new CreateSwimlaneOutputImpl();

        createSwimlaneInput.setName("Ready");
        createSwimlaneInput.setStageId(stageId);
        createSwimlaneInput.setWorkflowId(workflowId);

        createSwimlaneUseCase.execute(createSwimlaneInput,createSwimlaneOutput);

        Workflow workflow = WorkflowTransformer.toWorkflow(workflowRepository.getWorkFlowById(workflowId));

        assertEquals(1, workflow.getStageById(stageId).getSwimlanes().size());
        assertEquals("Ready", workflow.getStageById(stageId).getSwimlaneById(createSwimlaneOutput.getSwimlaneId()).getName());
    }

}
