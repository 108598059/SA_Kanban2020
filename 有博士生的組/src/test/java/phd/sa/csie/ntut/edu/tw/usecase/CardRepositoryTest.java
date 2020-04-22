package phd.sa.csie.ntut.edu.tw.usecase;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class CardRepositoryTest {

  @Test
  public void createCard() {
    CardRepository cardRepository = new CardRepository();
    Card card = new Card("test card");
    cardRepository.add(card);
    Card resultCard = cardRepository.findCardByUUID(card.getUUID());
    assertEquals(card.getName(), resultCard.getName());
    assertEquals(card.getUUID(), resultCard.getUUID());
  }

}