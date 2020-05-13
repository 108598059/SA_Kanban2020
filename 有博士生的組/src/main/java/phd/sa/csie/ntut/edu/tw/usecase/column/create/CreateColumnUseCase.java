package phd.sa.csie.ntut.edu.tw.usecase.column.create;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

public class CreateColumnUseCase
    extends UseCase<BoardRepository, BoardDTOConverter, CreateColumnUseCaseInput, CreateColumnUseCaseOutput> {

  public CreateColumnUseCase(BoardRepository repository, DomainEventBus eventBus, BoardDTOConverter dtoConverter) {
    super(repository, eventBus, dtoConverter);
  }

  public void execute(CreateColumnUseCaseInput createColumnUseCaseInput,
      CreateColumnUseCaseOutput createColumnUseCaseOutput) {
    String title = createColumnUseCaseInput.getTitle();
    UUID boardId = createColumnUseCaseInput.getBoardId();

    Board board = this.dtoConverter.toEntity(this.repository.findById(boardId));
    UUID columnId = board.createColumn(title);

    this.repository.save(this.dtoConverter.toDTO(board));
    createColumnUseCaseOutput.setId(columnId.toString());
  }

}