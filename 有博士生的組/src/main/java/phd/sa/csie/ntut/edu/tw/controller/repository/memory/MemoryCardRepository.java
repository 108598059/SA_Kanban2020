package phd.sa.csie.ntut.edu.tw.controller.repository.memory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class MemoryCardRepository extends CardRepository {

  private Map<UUID, CardDTO> storage;

  public MemoryCardRepository() {
    this.storage = new HashMap<>();
  }

  @Override
  public void save(CardDTO cardDTO) {
    this.storage.put(cardDTO.getId(), cardDTO);
  }

  @Override
  public CardDTO findById(UUID id) {
    return this.storage.get(id);
  }
}