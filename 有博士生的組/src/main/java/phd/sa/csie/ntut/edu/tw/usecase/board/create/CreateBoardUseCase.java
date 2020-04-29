package phd.sa.csie.ntut.edu.tw.usecase.board.create;

import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

public class CreateBoardUseCase {
  private BoardRepository boardRepository;

  public CreateBoardUseCase(BoardRepository repository) {
    this.boardRepository = repository;
  }

  public void execute(CreateBoardUseCaseInput input, CreateBoardUseCaseOutput output) {
    String title = input.getTitle();

    Board board = new Board(title);
    boardRepository.add(board);

    output.setBoardId(board.getUUID().toString());
    output.setBoardTitle(board.getName());
  }
}