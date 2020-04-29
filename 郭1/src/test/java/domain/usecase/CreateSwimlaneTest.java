package domain.usecase;

import domain.adapter.WorkflowRepositoryImpl;
import domain.controller.*;
import domain.entity.DomainEventBus;
import domain.usecase.stage.create.CreateStageInput;
import domain.usecase.stage.create.CreateStageOutput;
import domain.usecase.stage.create.CreateStageUseCase;
import domain.usecase.swimlane.create.CreateSwimlaneInput;
import domain.usecase.swimlane.create.CreateSwimlaneOutput;
import domain.usecase.swimlane.create.CreateSwimlaneUseCase;
import domain.usecase.workflow.create.CreateWorkflowInput;
import domain.usecase.workflow.create.CreateWorkflowOutput;
import domain.usecase.workflow.create.CreateWorkflowUseCase;
import org.junit.Before;
import org.junit.Test;

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

        CreateStageUseCase createStage = new CreateStageUseCase(workflowRepository) ;
        CreateStageInput createStageInput = new CreateStageInputImpl() ;
        CreateStageOutput createStageOutput = new CreateStageOutputImpl() ;

        createStageInput.setStageName("backlog");
        createStageInput.setWorkflowId(workflowId);
        createStage.execute( createStageInput, createStageOutput ) ;
        stageId = createStageOutput.getStageId();

    }
    @Test
    public void createSwimlane() {

        CreateSwimlaneUseCase createSwimlaneUseCase = new CreateSwimlaneUseCase(workflowRepository);
        CreateSwimlaneInput createSwimlaneInput = new CreateSwimlaneInputImpl();
        CreateSwimlaneOutput createSwimlaneOutput = new CreateSwimlaneOutputImpl();

        createSwimlaneInput.setName("Ready");
        createSwimlaneInput.setStageId(stageId);
        createSwimlaneInput.setWorkflowId(workflowId);

        createSwimlaneUseCase.execute(createSwimlaneInput,createSwimlaneOutput);


        assertNotNull(createSwimlaneOutput.getSwimlaneId());
    }

}
