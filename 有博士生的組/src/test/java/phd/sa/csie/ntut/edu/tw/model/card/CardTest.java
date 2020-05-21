package phd.sa.csie.ntut.edu.tw.model.card;

import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.event.CardCreatedEvent;

import java.util.UUID;

import static org.junit.Assert.*;

public class CardTest {
  @Test
  public void card_should_issue_card_created_event_when_constructed() {
    Card card = new Card("create card", new Board(UUID.randomUUID(), "Kanban"));

    assertEquals(1, card.getDomainEvents().size());
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
}