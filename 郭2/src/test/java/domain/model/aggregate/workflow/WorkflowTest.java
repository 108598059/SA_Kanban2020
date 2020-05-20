package domain.model.aggregate.workflow;

import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class WorkflowTest {
    private Workflow workflow;

    @Before
    public void SetUp(){

    }

    @Test
    public void create_a_workflow_should_generate_a_WorkflowCreated_event_in_the_domainEvent_list() {
        workflow = new Workflow("Default", "boardId");
        assertThat(workflow.getDomainEvents().size()).isEqualTo(1);
        assertThat(workflow.getDomainEvents().get(0).detail()).startsWith("WorkflowCreated");

        workflow.clearDomainEvents();
        assertThat(workflow.getDomainEvents().size()).isEqualTo(0);
    }
}
