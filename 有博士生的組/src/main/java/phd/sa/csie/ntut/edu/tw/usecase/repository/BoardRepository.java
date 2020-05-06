package phd.sa.csie.ntut.edu.tw.usecase.repository;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;

public interface BoardRepository {

  public void add(Board board);

  public Board findBoardByUUID(UUID uuid);

}