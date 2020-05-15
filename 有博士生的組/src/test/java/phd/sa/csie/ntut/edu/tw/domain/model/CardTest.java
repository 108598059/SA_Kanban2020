package phd.sa.csie.ntut.edu.tw.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.domain.model.card.event.CardCreated;

import java.util.UUID;

public class CardTest {

  private UUID fakeBoardId = UUID.randomUUID();
  private UUID fakeColumnId = UUID.randomUUID();

  @Test
  public void createCard() {
    Card card = new Card("Unit test for Card entity.", fakeBoardId, fakeColumnId);

    assertEquals("Unit test for Card entity.", card.getName());
    assertNotEquals("", card.getId().toString());
    assertNotNull(card.getId());

    assertEquals(fakeBoardId, card.getBoardId());
    assertEquals(fakeColumnId, card.getColumnId());
  }

  @Test
  public void cardCreatedEvent() {
    Card card = new Card("Unit test for Card entity.", fakeBoardId, fakeColumnId);

    assertEquals(1, card.getDomainEvents().size());
    assertEquals(CardCreated.class, card.getDomainEvents().get(0).getClass());
  }
}