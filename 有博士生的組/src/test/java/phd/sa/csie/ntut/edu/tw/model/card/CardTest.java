package phd.sa.csie.ntut.edu.tw.model.card;

import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.event.calculate.LeadTimeCalculatedEvent;
import phd.sa.csie.ntut.edu.tw.model.card.event.edit.CardBelongsColumnSetEvent;
import phd.sa.csie.ntut.edu.tw.model.card.event.create.CardCreatedEvent;
import phd.sa.csie.ntut.edu.tw.model.card.event.edit.CardNameSetEvent;

import java.util.UUID;

import static org.junit.Assert.*;

public class CardTest {
    @Test
    public void create_card_should_issue_card_created_event() {
        Card card = new Card("create card", new Board(UUID.randomUUID(), "Kanban"));

        assertEquals(2, card.getDomainEvents().size());
        assertEquals(CardCreatedEvent.class, card.getDomainEvents().get(0).getClass());
    }

    @Test
    public void card_name_is_empty_should_raise_illegal_argument_exception() {
        try {
            new Card("", new Board(UUID.randomUUID(), "Kanban"));
        } catch (IllegalArgumentException e) {
            assertEquals("Card name should not be empty", e.getMessage());
            return;
        }
        fail("Card name is empty should raise IllegalArgumentException.");
    }

    @Test
    public void card_name_is_null_should_raise_illegal_argument_exception() {
        try {
            new Card(null, new Board(UUID.randomUUID(), "Kanban"));
        } catch (IllegalArgumentException e) {
            assertEquals("Card name should not be empty", e.getMessage());
            return;
        }
        fail("Card name is null should raise IllegalArgumentException.");
    }

    @Test
    public void set_card_name_should_issue_card_name_set_event() {
        Board board = new Board(UUID.randomUUID(), "Kanban");
        Card card = new Card("Create card", board);

        assertEquals(2, card.getDomainEvents().size());
        card.setName("Set card name");
        assertEquals(3, card.getDomainEvents().size());
        assertEquals(CardNameSetEvent.class, card.getDomainEvents().get(2).getClass());
    }

    @Test
    public void set_lead_time_should_issue_lead_time_calculated_event() {
        Board board = new Board(UUID.randomUUID(), "Kanban");
        Card card = new Card("Create card", board);

        assertEquals(2, card.getDomainEvents().size());
        card.setLeadTime(24 * 60 * 60 * 1000);
        assertEquals(3, card.getDomainEvents().size());
        assertEquals(LeadTimeCalculatedEvent.class, card.getDomainEvents().get(2).getClass());
    }
}