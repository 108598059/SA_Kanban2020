package phd.sa.csie.ntut.edu.tw.controller.repository.memory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

public class MemoryBoardRepository implements BoardRepository {

  private Map<UUID, Board> boards;

  public MemoryBoardRepository() {
    this.boards = new HashMap<UUID, Board>();
  }

  public void add(Board board) {
    this.boards.put(board.getUUID(), board);
  }

  public Board findBoardByUUID(UUID uuid) {
    return this.boards.get(uuid);
  }

}