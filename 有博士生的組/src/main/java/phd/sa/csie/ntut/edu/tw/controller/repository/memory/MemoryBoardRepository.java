package phd.sa.csie.ntut.edu.tw.controller.repository.memory;

import java.util.HashMap;
import java.util.Map;

import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

public class MemoryBoardRepository extends BoardRepository {

  private Map<String, BoardDTO> storage;

  public MemoryBoardRepository() {
    this.storage = new HashMap<>();
  }

  @Override
  public void save(BoardDTO boardDTO) {
    this.storage.put(boardDTO.getId(), boardDTO);
  }

  @Override
  public void update(BoardDTO dto) {
    this.save(dto);
  }

  @Override
  public BoardDTO findById(String id) {
    return this.storage.get(id);
  }
}