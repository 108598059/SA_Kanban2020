package phd.sa.csie.ntut.edu.tw.controller.repository.memory;

import java.util.Map;
import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

public class MemoryBoardRepository implements BoardRepository {

  private Map<UUID, Board> boards;

  public MemoryBoardRepository(Map<UUID, Board> storage) {
    this.boards = storage;
  }

  public void add(Board board) {
    this.boards.put(board.getId(), board);
  }

  public Board findBoardById(UUID uuid) {
    return this.boards.get(uuid);
  }

}