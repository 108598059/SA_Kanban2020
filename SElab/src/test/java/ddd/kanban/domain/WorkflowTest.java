package ddd.kanban.domain;

import ddd.kanban.domain.model.kanbanboard.workflow.Workflow;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class WorkflowTest {

    @Test
    public void testCreateWorkflowShouldGenerateWorkflowCreatedDomainEvent(){
        Workflow workflow = new Workflow(UUID.randomUUID().toString(), "Default workflow", UUID.randomUUID().toString());

        assertEquals(1, workflow.getDomainEvents().size());
        workflow.clearDomainEvents();
    }
}
