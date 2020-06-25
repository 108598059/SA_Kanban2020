package tw.teddysoft.clean.domain.model.kanbanboard.workflow;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class WorkflowTest {

    private Workflow workflow;
    private String backlogStageId;

    @Before
    public void mySetUp(){
        creating_a_workflow_should_generate_a_WorkflowCreated_event();
        creating_a_stage_should_generate_a_StageCreated_event();
    }

    private void creating_a_workflow_should_generate_a_WorkflowCreated_event() {
            workflow = new Workflow("Default", "BOARD_lID");
            assertThat(workflow.getDomainEvents().size()).isEqualTo(1);
            assertThat(workflow.getDomainEvents().get(0).detail()).startsWith("WorkflowCreated");
            workflow.clearDomainEvents();
    }

    private void creating_a_stage_should_generate_a_StageCreated_event() {
        backlogStageId = workflow.addStage("Backlog").getId();
        assertThat(workflow.getDomainEvents().size()).isEqualTo(1);
        assertThat(workflow.getDomainEvents().get(0).detail()).startsWith("StageCreated");
        workflow.clearDomainEvents();
    }

    @Test
    public void committing_a_card_should_generate_a_CardCommitted_event() {
            workflow.commitCard("this_is_card_id_123", backlogStageId);
            assertThat(workflow.getDomainEvents().size()).isEqualTo(1);
            assertThat(workflow.getDomainEvents().get(0).detail()).startsWith("CardCommitted");
            workflow.clearDomainEvents();
    }

    @Test
    public void uncommitting_a_card_should_generate_a_CardUncommitted_event() {
        committing_a_card_should_generate_a_CardCommitted_event();

        workflow.uncommitCard("this_is_card_id_123", backlogStageId);
        assertThat(workflow.getDomainEvents().size()).isEqualTo(1);
        assertThat(workflow.getDomainEvents().get(0).detail()).startsWith("CardUncommitted");
        workflow.clearDomainEvents();
    }

    @Test
    public void moving_a_card_should_generate_CardUncommitted_and_CardCommitted_events() {

        String analysisStageId = workflow.addStage("Analysis").getId();
        workflow.clearDomainEvents();

        workflow.commitCard("this_is_card_id_123", backlogStageId);
        assertThat(workflow.getDomainEvents().size()).isEqualTo(1);
        assertThat(workflow.getDomainEvents().get(0).detail()).startsWith("CardCommitted");
        workflow.clearDomainEvents();

        workflow.moveCard("this_is_card_id_123", backlogStageId, analysisStageId);
        assertThat(workflow.getDomainEvents().size()).isEqualTo(2);
        assertThat(workflow.getDomainEvents().get(0).detail()).startsWith("CardUncommitted");
        assertThat(workflow.getDomainEvents().get(1).detail()).startsWith("CardCommitted");
        workflow.clearDomainEvents();
    }




    @Test
    public void moving_a_non_committed_card_should_throw_a_runtime_exception() {
        String analysisStageId = workflow.addStage("Analysis").getId();
        try{
            workflow.moveCard("this_is_card_id_123", backlogStageId, analysisStageId);
            fail();
        }
        catch (RuntimeException e){
            assertThat(e.getMessage()).startsWith("Cannot move card 'this_is_card_id_123' which does not belong to lane");
        }
    }

    @Test
    public void moving_a_card_from_a_nonexisting_land_should_throw_a_runtime_exception() {
        String analysisStageId = workflow.addStage("Analysis").getId();
        try{
            workflow.moveCard("this_is_card_id_123", "",  analysisStageId);
            fail();
        }
        catch (RuntimeException e){
            assertThat(e.getMessage()).startsWith("Cannot uncommit a card from a non-existing land");
        }
    }

    @Test
    public void moving_a_card_to_a_nonexisting_land_should_throw_a_runtime_exception() {
        try{
            workflow.moveCard("this_is_card_id_123", backlogStageId, "");
            fail();
        }
        catch (RuntimeException e){
            assertThat(e.getMessage()).startsWith("Cannot commit a card to a non-existing land");
        }
    }
}
