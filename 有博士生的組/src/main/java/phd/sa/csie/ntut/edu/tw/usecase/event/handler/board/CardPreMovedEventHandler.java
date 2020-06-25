package phd.sa.csie.ntut.edu.tw.usecase.event.handler.board;

import com.google.common.eventbus.Subscribe;
import phd.sa.csie.ntut.edu.tw.model.aggregate.board.Board;
import phd.sa.csie.ntut.edu.tw.model.aggregate.board.event.move.CardPreMovedEvent;
import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.board.move.MoveCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.move.MoveCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.board.move.MoveCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.DomainEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;

import java.util.UUID;

public class CardPreMovedEventHandler implements DomainEventHandler<CardPreMovedEvent> {
    private DomainEventBus eventBus;
    private BoardRepository boardRepository;

    public CardPreMovedEventHandler(DomainEventBus eventBus, BoardRepository boardRepository) {
        this.eventBus = eventBus;
        this.boardRepository = boardRepository;
    }

    @Subscribe
    @Override
    public void listen(CardPreMovedEvent cardPreMovedEvent) {
        String toColumnID = cardPreMovedEvent.getToColumnID();
        String fromColumnID = cardPreMovedEvent.getFromColumnID();
        String cardID = cardPreMovedEvent.getCardID();
        Board board = BoardDTOConverter.toEntity(this.boardRepository.findByID(cardPreMovedEvent.getBoardID()));

        if (board.checkWIP(UUID.fromString(toColumnID))) {
            MoveCardUseCase moveCardUseCase = new MoveCardUseCase(this.eventBus, this.boardRepository);
            MoveCardUseCaseInput input = new MoveCardUseCaseInput();
            input.setFromColumnID(fromColumnID);
            input.setToColumnID(toColumnID);
            input.setBoardID(cardPreMovedEvent.getBoardID());
            input.setCardID(cardID);
            moveCardUseCase.execute(input, new MoveCardUseCaseOutput());
        } else {
            board.releasePreservedPosition(UUID.fromString(toColumnID), UUID.fromString(cardID));
            this.boardRepository.update(BoardDTOConverter.toDTO(board));
        }
    }
}
