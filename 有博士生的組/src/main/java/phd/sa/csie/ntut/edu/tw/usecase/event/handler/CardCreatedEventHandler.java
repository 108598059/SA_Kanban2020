package phd.sa.csie.ntut.edu.tw.usecase.event.handler;

import com.google.common.eventbus.Subscribe;
import phd.sa.csie.ntut.edu.tw.model.card.event.CardCreatedEvent;
import phd.sa.csie.ntut.edu.tw.usecase.board.commit.card.CommitCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.board.commit.card.CommitCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.board.commit.card.CommitCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class CardCreatedEventHandler implements DomainEventHandler<CardCreatedEvent> {
    private CardRepository cardRepository;
    private BoardRepository boardRepository;

    public CardCreatedEventHandler(CardRepository cardRepository, BoardRepository boardRepository) {
        this.cardRepository = cardRepository;
        this.boardRepository = boardRepository;
    }

    @Subscribe
    @Override
    public void listen(CardCreatedEvent e) {
        CommitCardUseCaseInput input = new CommitCardUseCaseInput();
        input.setBoardID(e.getBoardID().toString());
        input.setCardID(e.getEntity().getID().toString());

        CommitCardUseCase commitCard = new CommitCardUseCase(this.cardRepository, this.boardRepository);
        commitCard.execute(input, new CommitCardUseCaseOutput());
    }
}
