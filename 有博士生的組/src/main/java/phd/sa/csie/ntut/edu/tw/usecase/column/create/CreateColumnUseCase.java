package phd.sa.csie.ntut.edu.tw.usecase.column.create;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

public class CreateColumnUseCase extends UseCase<CreateColumnUseCaseInput, CreateColumnUseCaseOutput> {
  private BoardRepository boardRepository;
  private BoardDTOConverter boardDTOConverter;
  public CreateColumnUseCase(BoardRepository boardRepository, DomainEventBus eventBus, BoardDTOConverter boardDTOConverter) {
    super(eventBus);
    this.boardRepository = boardRepository;
    this.boardDTOConverter = boardDTOConverter;
  }

  public void execute(CreateColumnUseCaseInput createColumnUseCaseInput,
      CreateColumnUseCaseOutput createColumnUseCaseOutput) {
    String title = createColumnUseCaseInput.getTitle();
    UUID boardId = createColumnUseCaseInput.getBoardId();

    Board board = this.boardDTOConverter.toEntity(this.boardRepository.findById(boardId.toString()));
    UUID columnId = board.createColumn(title);

    this.boardRepository.save(this.boardDTOConverter.toDTO(board));
    createColumnUseCaseOutput.setId(columnId.toString());
  }
}