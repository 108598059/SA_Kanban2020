package phd.sa.csie.ntut.edu.tw.usecase;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;
import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryCardRepository;

public class CardRepositoryTest {

  @Test
  public void createCard() {
    CardRepository cardRepository = new MemoryCardRepository();
    Card card = new Card("test card", UUID.randomUUID());
    card.setColumnId(UUID.randomUUID());

    CardDTO cardDTO = CardDTOConverter.toDTO(card);

    cardRepository.save(cardDTO);

    CardDTO resultCardDTO = cardRepository.findById(card.getId().toString());
    Card resultCard = CardDTOConverter.toEntity(resultCardDTO);

    assertEquals(card.getName(), resultCard.getName());
    assertEquals(card.getId(), resultCard.getId());
  }
}