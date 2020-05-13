package phd.sa.csie.ntut.edu.tw.usecase.column.setwip;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

public class SetColumnWIPUseCase extends UseCase<SetColumnWIPUseCaseInput, SetColumnWIPUseCaseOutput> {
  private BoardRepository boardRepository;
  private BoardDTOConverter boardDTOConverter;
  public SetColumnWIPUseCase(BoardRepository boardRepository, DomainEventBus eventBus, BoardDTOConverter boardDTOConverter) {
    super(eventBus);
    this.boardRepository = boardRepository;
    this.boardDTOConverter = boardDTOConverter;
  }

  public void execute(SetColumnWIPUseCaseInput input, SetColumnWIPUseCaseOutput output) {
    UUID boardId = input.getBoardId();
    UUID columnId = input.getColumnId();
    int wip = input.getColumnWIP();

    Board board = this.boardDTOConverter.toEntity(this.boardRepository.findById(boardId.toString()));
    board.setColumnWIP(columnId, wip);

    this.boardRepository.save(this.boardDTOConverter.toDTO(board));
    output.setColumnId(columnId.toString());
    output.setColumnWIP(wip);
  }

}