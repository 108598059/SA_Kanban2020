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
    UUID boardID = input.getBoardID();
    UUID columnID = input.getColumnID();
    int wip = input.getColumnWIP();

    Board board = BoardDTOConverter.toEntity(this.boardRepository.findByID(boardID.toString()));
    board.setColumnWIP(columnID, wip);

    this.boardRepository.update(BoardDTOConverter.toDTO(board));
    output.setColumnID(columnID.toString());
    output.setColumnWIP(wip);
  }

}