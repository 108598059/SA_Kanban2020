package domain.usecase.workflow;

import domain.adapters.controller.workflow.input.CreateStageInputImpl;
import domain.adapters.controller.workflow.output.CreateStageOutputImpl;
import domain.adapters.controller.workflow.input.CreateWorkflowInputImpl;
import domain.adapters.controller.workflow.output.CreateWorkflowOutputImpl;
import domain.entity.DomainEventBus;
import domain.entity.aggregate.workflow.Workflow;
import domain.usecase.workflow.create.CreateStageInput;
import domain.usecase.workflow.create.CreateStageOutput;
import domain.usecase.workflow.create.CreateStageUseCase;
import domain.adapters.repository.WorkflowRepositoryImpl;
import domain.usecase.workflow.create.CreateWorkflowInput;
import domain.usecase.workflow.create.CreateWorkflowOutput;
import domain.usecase.workflow.create.CreateWorkflowUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateStageTest {
    private WorkflowRepositoryImpl workflowRepository;
    private String workflowId;
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

    }
    @Test
    public void createStageTest() {

        CreateStageUseCase createStage = new CreateStageUseCase(workflowRepository, eventBus) ;
        CreateStageInput createStageInput = new CreateStageInputImpl() ;
        CreateStageOutput createStageOutput = new CreateStageOutputImpl() ;

        createStageInput.setStageName("backlog");
        createStageInput.setWorkflowId(workflowId);

        createStage.execute( createStageInput, createStageOutput ) ;

        Workflow workflow = WorkflowTransformer.toWorkflow(workflowRepository.getWorkFlowById(workflowId));


        assertEquals(1,workflow.getStages().size());
        assertEquals("backlog",workflow.getStageById(createStageOutput.getStageId()).getName());
    }
}
