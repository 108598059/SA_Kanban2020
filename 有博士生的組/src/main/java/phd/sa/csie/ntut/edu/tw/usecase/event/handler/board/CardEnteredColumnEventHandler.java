package phd.sa.csie.ntut.edu.tw.usecase.event.handler.board;

import com.google.common.eventbus.Subscribe;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.board.event.move.CardEnteredColumnEvent;
import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.board.move.MoveCardBackUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.move.MoveCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.move.MoveCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.board.move.MoveCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.DomainEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;

import java.util.UUID;

public class CardEnteredColumnEventHandler implements DomainEventHandler<CardEnteredColumnEvent> {
    private DomainEventBus eventBus;
    private BoardRepository boardRepository;

    public CardEnteredColumnEventHandler(DomainEventBus eventBus, BoardRepository boardRepository) {
        this.eventBus = eventBus;
        this.boardRepository = boardRepository;

    }

    @Subscribe
    @Override
    public void listen(CardEnteredColumnEvent cardEnteredColumnEvent) {
        String toColumnID = cardEnteredColumnEvent.getToColumnID();
        String fromColumnID = cardEnteredColumnEvent.getFromColumnID();
        String cardID = cardEnteredColumnEvent.getCardID();
        Board board = BoardDTOConverter.toEntity(this.boardRepository.findByID(cardEnteredColumnEvent.getBoardID()));

        if (board.checkWIP(UUID.fromString(toColumnID))) {
            board.releasePreservedPosition(UUID.fromString(fromColumnID), UUID.fromString(cardID));

        } else {
            MoveCardBackUseCase moveCardBackUseCase = new MoveCardBackUseCase(this.eventBus, this.boardRepository);
            MoveCardUseCaseInput input = new MoveCardUseCaseInput();

            input.setToColumnID(fromColumnID);
            input.setFromColumnID(toColumnID);
            input.setBoardID(cardEnteredColumnEvent.getBoardID());
            input.setCardID(cardID);

            moveCardBackUseCase.execute(input, new MoveCardUseCaseOutput());
        }



    }
}
