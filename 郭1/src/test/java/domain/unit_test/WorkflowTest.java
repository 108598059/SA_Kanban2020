package domain.unit_test;

import domain.entity.workflow.Workflow;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WorkflowTest {



    @Before
    public void setUp(){

    }



    @Test
    public void Create_workflow_should_create_domain_event(){
        Workflow workflow = new Workflow("3");

        assertEquals(1, workflow.getEvents().size());
    }
}
