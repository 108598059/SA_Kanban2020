package phd.sa.csie.ntut.edu.tw.controller.repository.memory;

import java.util.Map;
import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.usecase.DTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class MemoryCardRepository implements CardRepository {

  private Map<UUID, DTO> map;

  public MemoryCardRepository(Map<UUID, DTO> storage) {
    this.map = storage;
  }

  @Override
  public void add(DTO card) {
    this.map.put(card.getId(), card);
  }
  
  @Override
  public DTO findById(UUID uuid) {
    return this.map.get(uuid);
  }

  @Override
  public void save(DTO entity) {
    this.map.remove(entity.getId());
    this.map.put(entity.getId(), entity);
  }

}