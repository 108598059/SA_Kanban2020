package phd.sa.csie.ntut.edu.tw.usecase;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
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
    Map<UUID, CardDTO> storage = new HashMap<UUID, CardDTO>();
    CardRepository cardRepository = new MemoryCardRepository(storage);
    Card card = new Card("test card");

    CardDTOConverter dtoConverter = new CardDTOConverter();
    CardDTO cardDTO = dtoConverter.toDTO(card);

    cardRepository.save(cardDTO);

    CardDTO resultCardDTO = cardRepository.findById(card.getId());
    Card resultCard = (Card) dtoConverter.toEntity(resultCardDTO);

    assertEquals(card.getName(), resultCard.getName());
    assertEquals(card.getId(), resultCard.getId());
  }

}