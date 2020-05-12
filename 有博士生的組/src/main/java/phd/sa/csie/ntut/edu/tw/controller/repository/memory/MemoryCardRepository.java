package phd.sa.csie.ntut.edu.tw.controller.repository.memory;

import java.util.Map;
import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class MemoryCardRepository extends CardRepository {

  private Map<UUID, CardDTO> map;

  public MemoryCardRepository(Map<UUID, CardDTO> storage) {
    this.map = storage;
  }

  @Override
  public void save(CardDTO cardDTO) {
    this.map.put(cardDTO.getId(), cardDTO);
  }

  @Override
  public CardDTO findById(UUID id) {
    return this.map.get(id);
  }

  @Override
  public void update(CardDTO entity) {
    if (this.map.put(entity.getId(), entity) == null) {
      String id = entity.getId().toString();
      throw new RuntimeException("Cannot find the stored object by key: " + id);
    }
  }

}