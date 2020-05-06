package phd.sa.csie.ntut.edu.tw.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;

public class CardTest {

  @Test
  public void createCard() {
    Card card = new Card("create card");

    assertEquals("create card", card.getName());
    assertNotEquals("", card.getId().toString());
    assertNotNull(card.getId());
  }

  @Test
  public void cardCreatedEvent() {
    Card card = new Card("create card");
    assertEquals(1, card.getDomainEvents().size());
  }

}