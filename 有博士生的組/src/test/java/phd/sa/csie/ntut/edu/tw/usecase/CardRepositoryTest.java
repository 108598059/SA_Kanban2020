package phd.sa.csie.ntut.edu.tw.usecase;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;
import phd.sa.csie.ntut.edu.tw.controller.repository.memory.MemoryCardRepository;

public class CardRepositoryTest {

  @Test
  public void createCard() {
    Map<UUID, DTO> storage = new HashMap<UUID, DTO>();
    CardRepository cardRepository = new MemoryCardRepository(storage);
    Card card = new Card("test card");

    DTOConverter dtoConverter = new DTOConverter();
    DTO cardDTO = dtoConverter.toDTO(card);

    cardRepository.add(cardDTO);

    Card resultCard = (Card) dtoConverter.toEntity(cardRepository.findById(cardDTO.getId()));

    assertEquals(card.getName(), resultCard.getName());
    assertEquals(card.getId(), resultCard.getId());
  }

}