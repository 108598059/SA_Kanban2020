package phd.sa.csie.ntut.edu.tw.usecase.card.move;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
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
    BoardDTO boardDTO = this.boardRepository.findById(moveCardUseCaseInput.getBoardId().toString());
    Board board = BoardDTOConverter.toEntity(boardDTO);
    UUID cardId = moveCardUseCaseInput.getCardId();
    UUID fromColumnId = moveCardUseCaseInput.getFromColumnId();
    UUID toColumnId = moveCardUseCaseInput.getToColumnId();

    String newColumnId = board.moveCard(cardId, fromColumnId, toColumnId);

    this.boardRepository.update(BoardDTOConverter.toDTO(board));
    this.eventBus.postAll(board);

    moveCardUseCaseOutput.setCardId(cardId);
    moveCardUseCaseOutput.setOldColumnId(fromColumnId.toString());
    moveCardUseCaseOutput.setNewColumnId(newColumnId);
  }

}