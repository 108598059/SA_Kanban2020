package phd.sa.csie.ntut.edu.tw.model.card;

import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.board.event.CardLeftColumnEvent;
import phd.sa.csie.ntut.edu.tw.model.card.event.CardBelongsColumnSetEvent;
import phd.sa.csie.ntut.edu.tw.model.card.event.CardCreatedEvent;
import phd.sa.csie.ntut.edu.tw.model.card.event.CardNameSetEvent;

import java.util.UUID;

import static org.junit.Assert.*;

public class CardTest {
  @Test
  public void create_card_should_issue_card_created_event() {
    Card card = new Card("create card", new Board(UUID.randomUUID(), "Kanban"));

    assertEquals(3, card.getDomainEvents().size());
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
  public void set_belongs_column_id_should_issue_card_belongs_column_set_event() {
    Board board = new Board(UUID.randomUUID(), "Kanban");
    Card card = new Card("Create card", board);
    UUID archiveColumnID = board.getArchiveColumn().getID();

    assertEquals(3, card.getDomainEvents().size());
    card.setBelongsColumnID(archiveColumnID);
    assertEquals(4, card.getDomainEvents().size());
    assertEquals(CardBelongsColumnSetEvent.class, card.getDomainEvents().get(3).getClass());
  }

  @Test
  public void set_card_name_should_issue_card_name_set_event() {
    Board board = new Board(UUID.randomUUID(), "Kanban");
    Card card = new Card("Create card", board);

    assertEquals(3, card.getDomainEvents().size());
    card.setName("Set card name");
    assertEquals(4, card.getDomainEvents().size());
    assertEquals(CardNameSetEvent.class, card.getDomainEvents().get(3).getClass());
  }
}