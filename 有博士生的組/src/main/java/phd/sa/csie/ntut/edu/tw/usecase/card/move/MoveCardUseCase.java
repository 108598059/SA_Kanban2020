package phd.sa.csie.ntut.edu.tw.usecase.card.move;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

public class MoveCardUseCase
    extends UseCase<BoardRepository, BoardDTOConverter, MoveCardUseCaseInput, MoveCardUseCaseOutput> {

  public MoveCardUseCase(BoardRepository repository, DomainEventBus eventBus, BoardDTOConverter dtoConverter) {
    super(repository, eventBus, dtoConverter);
  }

  public void execute(MoveCardUseCaseInput moveCardUseCaseInput, MoveCardUseCaseOutput moveCardUseCaseOutput) {
    BoardDTO boardDTO = this.repository.findById(moveCardUseCaseInput.getBoardId());
    Board board = this.dtoConverter.toEntity(boardDTO);
    UUID cardId = moveCardUseCaseInput.getCardId();
    UUID fromColumnId = moveCardUseCaseInput.getFromColumnId();
    UUID toColumnId = moveCardUseCaseInput.getToColumnId();

    String newColumnId = board.moveCard(cardId, fromColumnId, toColumnId);

    this.repository.save(this.dtoConverter.toDTO(board));
    this.eventBus.postAll(board);

    moveCardUseCaseOutput.setCardId(cardId);
    moveCardUseCaseOutput.setOldColumnId(fromColumnId.toString());
    moveCardUseCaseOutput.setNewColumnId(newColumnId);
  }

}