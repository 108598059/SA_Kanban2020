package phd.sa.csie.ntut.edu.tw.usecase.column.setwip;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

public class SetColumnWIPUseCase extends UseCase<SetColumnWIPUseCaseInput, SetColumnWIPUseCaseOutput> {
  private BoardRepository boardRepository;
  public SetColumnWIPUseCase(BoardRepository boardRepository, DomainEventBus eventBus) {
    super(eventBus);
    this.boardRepository = boardRepository;
  }

  public void execute(SetColumnWIPUseCaseInput input, SetColumnWIPUseCaseOutput output) {
    UUID boardId = input.getBoardId();
    UUID columnId = input.getColumnId();
    int wip = input.getColumnWIP();

    Board board = BoardDTOConverter.toEntity(this.boardRepository.findById(boardId.toString()));
    board.setColumnWIP(columnId, wip);

    this.boardRepository.update(BoardDTOConverter.toDTO(board));
    output.setColumnId(columnId.toString());
    output.setColumnWIP(wip);
  }

}