package phd.sa.csie.ntut.edu.tw.usecase.column.create;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

public class CreateColumnUseCase extends UseCase<CreateColumnUseCaseInput, CreateColumnUseCaseOutput> {
  private BoardRepository boardRepository;

  public CreateColumnUseCase(BoardRepository boardRepository, DomainEventBus eventBus) {
    super(eventBus);
    this.boardRepository = boardRepository;
  }

  public void execute(CreateColumnUseCaseInput createColumnUseCaseInput,
      CreateColumnUseCaseOutput createColumnUseCaseOutput) {
    String title = createColumnUseCaseInput.getTitle();
    UUID boardID = createColumnUseCaseInput.getBoardID();

    Board board = BoardDTOConverter.toEntity(this.boardRepository.findByID(boardID.toString()));
    UUID columnID = board.createColumn(title);

    this.boardRepository.update(BoardDTOConverter.toDTO(board));
    createColumnUseCaseOutput.setID(columnID.toString());
  }
}