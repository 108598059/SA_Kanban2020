package ddd.kanban.domain;

import ddd.kanban.domain.model.kanbanboard.workflow.Workflow;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class WorkflowTest {

    @Test
    public void testCreateWorkflowShouldGenerateWorkflowCreatedDomainEvent(){
        String boardId = UUID.randomUUID().toString();
        String workflowId = UUID.randomUUID().toString();
        Workflow workflow = new Workflow(workflowId, "Default workflow", boardId);

        assertEquals(1, workflow.getDomainEvents().size());
        workflow.clearDomainEvents();
        assertEquals(0, workflow.getDomainEvents().size());
    }
}
