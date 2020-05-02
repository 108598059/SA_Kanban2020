package phd.sa.csie.ntut.edu.tw.usecase.column.setwip;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

public class SetColumnWIPUseCase {

  private BoardRepository boardRepository;

  public SetColumnWIPUseCase(BoardRepository repository) {
    this.boardRepository = repository;
  }

  public void execute(SetColumnWIPUseCaseInput input, SetColumnWIPUseCaseOutput output) {
    UUID boardId = input.getBoardId();
    UUID columnId = input.getColumnId();
    int wip = input.getColumnWIP();

    Board board = boardRepository.findBoardByUUID(boardId);
    board.setColumnWIP(columnId, wip);

    boardRepository.add(board);
    output.setColumnId(columnId.toString());
    output.setColumnWIP(wip);
  }

}