package ddd.kanban.usecase.workflow;

import ddd.kanban.adapter.repository.workflow.InMemoryWorkflowRepository;
import ddd.kanban.usecase.workflow.create.CreateWorkflowInput;
import ddd.kanban.usecase.workflow.create.CreateWorkflowOutput;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.workflow.create.CreateWorkflowUseCase;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateWorkflowUseCaseTest {
    private WorkflowRepository inMemoryWorkflowRepository;
    private String workflowId;
    private String workflowboardId;
    @Before
    public void setUp(){
        workflowId = UUID.randomUUID().toString();
        workflowboardId=UUID.randomUUID().toString();
        inMemoryWorkflowRepository = new InMemoryWorkflowRepository();
    }
    @Test
    public void testCreateWorkflow() {
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(inMemoryWorkflowRepository);

        CreateWorkflowInput createWorkflowInput = new CreateWorkflowInput(workflowId,"Workflow 1",workflowboardId);
        CreateWorkflowOutput createWorkflowOutput = new CreateWorkflowOutput();

        createWorkflowUseCase.execute(createWorkflowInput, createWorkflowOutput);

        assertEquals("Workflow 1", createWorkflowOutput.getWorkflowTitle());
        assertNotNull(createWorkflowOutput.getWorkflowId());
        assertNotNull(createWorkflowOutput.getWorkflowBoardId());
    }
}
