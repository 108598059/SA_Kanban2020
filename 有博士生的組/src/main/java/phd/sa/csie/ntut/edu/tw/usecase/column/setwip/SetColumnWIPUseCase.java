package phd.sa.csie.ntut.edu.tw.usecase.column.setwip;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

public class SetColumnWIPUseCase
    extends UseCase<BoardRepository, BoardDTOConverter, SetColumnWIPUseCaseInput, SetColumnWIPUseCaseOutput> {

  public SetColumnWIPUseCase(BoardRepository repository, DomainEventBus eventBus, BoardDTOConverter dtoConverter) {
    super(repository, eventBus, dtoConverter);
  }

  public void execute(SetColumnWIPUseCaseInput input, SetColumnWIPUseCaseOutput output) {
    UUID boardId = input.getBoardId();
    UUID columnId = input.getColumnId();
    int wip = input.getColumnWIP();

    Board board = this.dtoConverter.toEntity(this.repository.findById(boardId));
    board.setColumnWIP(columnId, wip);

    this.repository.save(this.dtoConverter.toDTO(board));
    output.setColumnId(columnId.toString());
    output.setColumnWIP(wip);
  }

}