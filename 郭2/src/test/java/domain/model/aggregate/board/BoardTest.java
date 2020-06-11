package domain.model.aggregate.board;

import domain.model.aggregate.card.Card;
import domain.model.aggregate.workflow.Lane;
import domain.model.aggregate.workflow.Workflow;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class BoardTest {
    private Board board;

    @Before
    public void SetUp() {

    }

    @Test
    public void create_board_should_generate_BoardCreated_event_in_the_domainEvent_list() {
        board = new Board("boardName");
        assertThat(board.getDomainEvents().size()).isEqualTo(1);
        assertThat(board.getDomainEvents().get(0).detail()).startsWith("BoardCreated");

        board.clearDomainEvents();
        assertThat(board.getDomainEvents().size()).isEqualTo(0);
    }

    @Test
    public void commit_workflow_should_generate_WorkflowCommited_in_the_domainEvent_list() {

        board = new Board("boardName");
        Workflow workflow = new Workflow("workflowName",board.getBoardId());

        board.addWorkflowId(workflow.getWorkflowId());

        assertThat(board.getDomainEvents().size()).isEqualTo(2);
        assertThat(board.getDomainEvents().get(1).detail()).startsWith("WorkflowCommited");

        board.clearDomainEvents();
        assertThat(board.getDomainEvents().size()).isEqualTo(0);
    }
}