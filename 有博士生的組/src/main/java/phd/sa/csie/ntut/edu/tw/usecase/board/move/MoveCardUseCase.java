package phd.sa.csie.ntut.edu.tw.usecase.board.move;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

public class MoveCardUseCase extends UseCase<MoveCardUseCaseInput, MoveCardUseCaseOutput> {
  private BoardRepository boardRepository;

  public MoveCardUseCase(BoardRepository boardRepository, DomainEventBus eventBus) {
    super(eventBus);
    this.boardRepository = boardRepository;
  }

  public void execute(MoveCardUseCaseInput moveCardUseCaseInput, MoveCardUseCaseOutput moveCardUseCaseOutput) {
    Board board = BoardDTOConverter.toEntity(this.boardRepository.findByID(moveCardUseCaseInput.getBoardID().toString()));
    String cardID = moveCardUseCaseInput.getCardID();
    String fromColumnID = moveCardUseCaseInput.getFromColumnID();
    String toColumnID = moveCardUseCaseInput.getToColumnID();

    String newColumnID = board.moveCard(UUID.fromString(cardID),
                                        UUID.fromString(fromColumnID),
                                        UUID.fromString(toColumnID));

    this.boardRepository.update(BoardDTOConverter.toDTO(board));
    this.eventBus.postAll(board);

    moveCardUseCaseOutput.setCardID(cardID);
    moveCardUseCaseOutput.setOldColumnID(fromColumnID);
    moveCardUseCaseOutput.setNewColumnID(newColumnID);
  }

}