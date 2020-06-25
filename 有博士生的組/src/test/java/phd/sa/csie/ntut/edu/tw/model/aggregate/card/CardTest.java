package phd.sa.csie.ntut.edu.tw.model.aggregate.card;

import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.model.aggregate.board.Board;
import phd.sa.csie.ntut.edu.tw.model.aggregate.card.event.create.CardCreatedEvent;
import phd.sa.csie.ntut.edu.tw.model.aggregate.card.event.edit.CardNameEditedEvent;

import java.util.UUID;

import static org.junit.Assert.*;

public class CardTest {
    @Test
    public void create_card_should_issue_card_created_event() {
        Card card = new Card("create card", new Board(UUID.randomUUID(), "Kanban").getID().toString());

        assertEquals(1, card.getDomainEvents().size());
        assertEquals(CardCreatedEvent.class, card.getDomainEvents().get(0).getClass());
    }

    @Test
    public void card_name_is_empty_should_raise_illegal_argument_exception() {
        try {
            new Card("", new Board(UUID.randomUUID(), "Kanban").getID().toString());
        } catch (IllegalArgumentException e) {
            assertEquals("Card name should not be empty", e.getMessage());
            return;
        }
        fail("Card name is empty should raise IllegalArgumentException.");
    }

    @Test
    public void set_card_name_should_issue_card_name_set_event() {
        Card card = new Card("Create card", new Board(UUID.randomUUID(), "Kanban").getID().toString());

        assertEquals(1, card.getDomainEvents().size());
        card.updateName("Set card name");
        assertEquals(2, card.getDomainEvents().size());
        assertEquals(CardNameEditedEvent.class, card.getDomainEvents().get(1).getClass());
    }
}