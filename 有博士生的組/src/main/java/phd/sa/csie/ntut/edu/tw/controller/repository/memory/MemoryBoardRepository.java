package phd.sa.csie.ntut.edu.tw.controller.repository.memory;

import java.util.Map;
import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

public class MemoryBoardRepository extends BoardRepository {

  private Map<UUID, BoardDTO> storage;

  public MemoryBoardRepository(Map<UUID, BoardDTO> storage) {
    this.storage = storage;
  }

  @Override
  public void save(BoardDTO boardDTO) {
    this.storage.put(boardDTO.getId(), boardDTO);
  }

  @Override
  public BoardDTO findById(UUID id) {
    return this.storage.get(id);
  }

  @Override
  public void update(BoardDTO dto) {
    // TODO Auto-generated method stub
  }

}