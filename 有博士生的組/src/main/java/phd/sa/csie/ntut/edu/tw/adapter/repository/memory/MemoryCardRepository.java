package phd.sa.csie.ntut.edu.tw.adapter.repository.memory;

import java.util.HashMap;
import java.util.Map;

import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class MemoryCardRepository extends CardRepository {

  private Map<String, CardDTO> storage;

  public MemoryCardRepository() {
    this.storage = new HashMap<>();
  }

  @Override
  public void save(CardDTO cardDTO) {
    this.storage.put(cardDTO.getID(), cardDTO);
  }

  @Override
  public void update(CardDTO dto) {
    this.save(dto);
  }

  @Override
  public CardDTO findByID(String id) {
    return this.storage.get(id);
  }
}