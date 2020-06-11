package domain.model.aggregate.workflow;

import domain.model.aggregate.card.Card;
import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WorkflowTest {
    private Workflow workflow;

    @Before
    public void SetUp(){
        workflow = new Workflow("Default", "boardId");
    }

    @Test
    public void create_workflow_should_generate_a_WorkflowCreated_event_in_the_domainEvent_list() {

        assertThat(workflow.getDomainEvents().size()).isEqualTo(1);
        assertThat(workflow.getDomainEvents().get(0).detail()).startsWith("WorkflowCreated");

        workflow.clearDomainEvents();
        assertThat(workflow.getDomainEvents().size()).isEqualTo(0);
    }

    @Test
    public void create_stage_should_generate_LaneCreated_event_in_the_domainEvent_list() {

        workflow.createStage("stageName");

        assertThat(workflow.getDomainEvents().size()).isEqualTo(2);
        assertThat(workflow.getDomainEvents().get(1).detail()).startsWith("LaneCreated");

        workflow.clearDomainEvents();
        assertThat(workflow.getDomainEvents().size()).isEqualTo(0);
    }

    @Test
    public void create_swimlane_should_generate_LaneCreated_event_in_the_domainEvent_list() {

        workflow.createSwimlane("swimlaneName");

        assertThat(workflow.getDomainEvents().size()).isEqualTo(2);
        assertThat(workflow.getDomainEvents().get(1).detail()).startsWith("LaneCreated");

        workflow.clearDomainEvents();
        assertThat(workflow.getDomainEvents().size()).isEqualTo(0);
    }

    @Test
    public void commit_card_and_uncommit_card_should_generate_FlowEvent_in_the_domainEvent_list() {

        Lane swimlane = workflow.createSwimlane("swimlaneName");
        Card card = new Card("cardName", workflow.getWorkflowId() ,swimlane.getLaneId());

        workflow.addCardInLane(card.getLaneId(),card.getCardId());

        assertThat(workflow.getDomainEvents().size()).isEqualTo(3);
        assertThat(workflow.getDomainEvents().get(2).detail()).startsWith("FlowEvent");

        workflow.deleteCardFromLane(card.getLaneId(),card.getCardId());

        assertThat(workflow.getDomainEvents().size()).isEqualTo(4);
        assertThat(workflow.getDomainEvents().get(3).detail()).startsWith("FlowEvent");

        workflow.clearDomainEvents();
        assertThat(workflow.getDomainEvents().size()).isEqualTo(0);
    }

}
