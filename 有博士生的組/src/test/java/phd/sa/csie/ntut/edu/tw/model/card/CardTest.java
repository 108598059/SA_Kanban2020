package phd.sa.csie.ntut.edu.tw.model.card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.event.CardCreatedEvent;

import java.util.UUID;

public class CardTest {

  @Test
  public void createCard() {
    Card card = new Card("create card", new Board(UUID.randomUUID(), "Kanban"));

    assertEquals("create card", card.getName());
    assertNotEquals("", card.getID().toString());
    assertNotNull(card.getID());
  }

  @Test
  public void cardCreatedEvent() {
    Card card = new Card("create card", new Board(UUID.randomUUID(), "Kanban"));

    assertEquals(1, card.getDomainEvents().size());
    assertEquals(CardCreatedEvent.class, card.getDomainEvents().get(0).getClass());
  }
}