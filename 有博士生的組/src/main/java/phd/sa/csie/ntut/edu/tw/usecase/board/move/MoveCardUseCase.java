package phd.sa.csie.ntut.edu.tw.usecase.board.move;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

public class MoveCardUseCase extends UseCase<MoveCardUseCaseInput, MoveCardUseCaseOutput> {
  private BoardRepository boardRepository;

  public MoveCardUseCase(BoardRepository boardRepository, DomainEventBus eventBus) {
    super(eventBus);
    this.boardRepository = boardRepository;
  }

  public void execute(MoveCardUseCaseInput moveCardUseCaseInput, MoveCardUseCaseOutput moveCardUseCaseOutput) {
    BoardDTO boardDTO = this.boardRepository.findByID(moveCardUseCaseInput.getBoardID().toString());
    Board board = BoardDTOConverter.toEntity(boardDTO);
    UUID cardID = moveCardUseCaseInput.getCardID();
    UUID fromColumnID = moveCardUseCaseInput.getFromColumnID();
    UUID toColumnID = moveCardUseCaseInput.getToColumnID();

    String newColumnID = board.moveCard(cardID, fromColumnID, toColumnID);

    this.boardRepository.update(BoardDTOConverter.toDTO(board));
    this.eventBus.postAll(board);

    moveCardUseCaseOutput.setCardID(cardID);
    moveCardUseCaseOutput.setOldColumnID(fromColumnID.toString());
    moveCardUseCaseOutput.setNewColumnID(newColumnID);
  }

}