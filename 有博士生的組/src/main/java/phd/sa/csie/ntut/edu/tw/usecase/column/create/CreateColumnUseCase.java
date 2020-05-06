package phd.sa.csie.ntut.edu.tw.usecase.column.create;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

public class CreateColumnUseCase {

  private BoardRepository boardRepository;

  public CreateColumnUseCase(BoardRepository boardRepository) {
    this.boardRepository = boardRepository;
  }

  public void execute(CreateColumnUseCaseInput createColumnUseCaseInput,
      CreateColumnUseCaseOutput createColumnUseCaseOutput) {
    String title = createColumnUseCaseInput.getTitle();
    UUID boardId = createColumnUseCaseInput.getBoardId();

    Board board = this.boardRepository.findBoardById(boardId);
    UUID columnId = board.createColumn(title);

    this.boardRepository.add(board);
    createColumnUseCaseOutput.setId(columnId.toString());
  }

}