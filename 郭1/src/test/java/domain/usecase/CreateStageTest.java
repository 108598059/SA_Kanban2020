package domain.usecase;

import domain.adapters.controller.workflow.CreateStageInputImpl;
import domain.adapters.controller.workflow.CreateStageOutputImpl;
import domain.adapters.controller.workflow.CreateWorkflowInputImpl;
import domain.adapters.controller.workflow.CreateWorkflowOutputImpl;
import domain.entity.DomainEventBus;
import domain.usecase.stage.create.CreateStageInput;
import domain.usecase.stage.create.CreateStageOutput;
import domain.usecase.stage.create.CreateStageUseCase;
import domain.adapters.repository.WorkflowRepositoryImpl;
import domain.usecase.workflow.create.CreateWorkflowInput;
import domain.usecase.workflow.create.CreateWorkflowOutput;
import domain.usecase.workflow.create.CreateWorkflowUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

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

        CreateStageUseCase createStage = new CreateStageUseCase(workflowRepository) ;
        CreateStageInput createStageInput = new CreateStageInputImpl() ;
        CreateStageOutput createStageOutput = new CreateStageOutputImpl() ;

        createStageInput.setStageName("backlog");
        createStageInput.setWorkflowId(workflowId);

        createStage.execute( createStageInput, createStageOutput ) ;

        assertNotNull(createStageOutput.getStageId());
    }
}
