package phd.sa.csie.ntut.edu.tw.usecase.event.handler.card;

import com.google.common.eventbus.Subscribe;
import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.card.event.create.CardCreatedEvent;
import phd.sa.csie.ntut.edu.tw.usecase.board.commit.card.CommitCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.board.commit.card.CommitCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.board.commit.card.CommitCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.DomainEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.card.CardRepository;

public class CardCreatedEventHandler implements DomainEventHandler<CardCreatedEvent> {
    private CardRepository cardRepository;
    private BoardRepository boardRepository;
    private DomainEventBus eventBus;

    public CardCreatedEventHandler(DomainEventBus eventBus, CardRepository cardRepository, BoardRepository boardRepository) {
        this.eventBus = eventBus;
        this.cardRepository = cardRepository;
        this.boardRepository = boardRepository;
    }

    @Subscribe
    @Override
    public void listen(CardCreatedEvent e) {
        CommitCardUseCase commitCard = new CommitCardUseCase(this.eventBus, this.cardRepository, this.boardRepository);
        CommitCardUseCaseInput input = new CommitCardUseCaseInput();

        input.setBoardID(e.getBoardID().toString());
        input.setCardID(e.getEntity().getID().toString());

        commitCard.execute(input, new CommitCardUseCaseOutput());
    }
}
