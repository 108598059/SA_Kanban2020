package phd.sa.csie.ntut.edu.tw.usecase.card.move;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

public class MoveCardUseCase {

  private BoardRepository boardRepository;
  private DomainEventBus eventBus;

  public MoveCardUseCase(BoardRepository boardRepository, DomainEventBus eventBus) {
    this.boardRepository = boardRepository;
    this.eventBus = eventBus;
  }

  public void execute(MoveCardUseCaseInput moveCardUseCaseInput, MoveCardUseCaseOutput moveCardUseCaseOutput) {
    Board board = boardRepository.findBoardById(moveCardUseCaseInput.getBoardId());
    UUID cardId = moveCardUseCaseInput.getCardId();
    UUID fromColumnId = moveCardUseCaseInput.getFromColumnId();
    UUID toColumnId = moveCardUseCaseInput.getToColumnId();

    String newColumnId = board.moveCard(cardId, fromColumnId, toColumnId);

    boardRepository.add(board);
    this.eventBus.postAll(board);
    moveCardUseCaseOutput.setCardId(cardId);
    moveCardUseCaseOutput.setOldColumnId(fromColumnId.toString());
    moveCardUseCaseOutput.setNewColumnId(newColumnId);
  }

}