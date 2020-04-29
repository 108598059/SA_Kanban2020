package ddd.kanban.usecase.workflow;

import ddd.kanban.adapter.repository.workflow.InMemoryWorkflowRepository;
import ddd.kanban.domain.model.workflow.Workflow;
import ddd.kanban.usecase.workflow.create.*;
import ddd.kanban.usecase.repository.WorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static junit.framework.TestCase.assertEquals;

public class CreateColumnUseCaseTest {
    private WorkflowRepository inMemoryWorkflowRepository;
    private String workflowId;
    private String workflowboardId;
    @Before
    public void setUp(){
        workflowId = UUID.randomUUID().toString();

        inMemoryWorkflowRepository = new InMemoryWorkflowRepository();
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(inMemoryWorkflowRepository);
        CreateWorkflowInput createWorkflowInput = new CreateWorkflowInput(workflowId,"Workflow 1",workflowboardId);
        CreateWorkflowOutput createWorkflowOutput = new CreateWorkflowOutput();
        createWorkflowUseCase.execute(createWorkflowInput, createWorkflowOutput);
    }


    @Test
    public void testCreateColumn(){
        CreateColumnUseCase createColumnUseCase = new CreateColumnUseCase(inMemoryWorkflowRepository);
        CreateColumnInput createColumnInput = new CreateColumnInput("column", workflowId);
        CreateColumnOutput createColumnOutput = new CreateColumnOutput();

        createColumnUseCase.execute(createColumnInput, createColumnOutput);

        assertEquals("column", createColumnOutput.getColumnTitle());
    }
}
