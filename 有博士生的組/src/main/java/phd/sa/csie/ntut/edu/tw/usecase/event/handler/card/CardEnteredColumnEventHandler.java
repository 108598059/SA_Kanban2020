package phd.sa.csie.ntut.edu.tw.usecase.event.handler.card;

import com.google.common.eventbus.Subscribe;
import phd.sa.csie.ntut.edu.tw.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.event.CardEnteredColumnEvent;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.column.EditCardBelongsColumnUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.column.EditCardBelongsColumnUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.column.EditCardBelongsColumnUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.DomainEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class CardEnteredColumnEventHandler implements DomainEventHandler<CardEnteredColumnEvent> {
    private DomainEventBus eventBus;
    private CardRepository cardRepository;

    public CardEnteredColumnEventHandler(DomainEventBus eventBus, CardRepository cardRepository) {
        this.eventBus = eventBus;
        this.cardRepository = cardRepository;
    }

    @Subscribe
    @Override
    public void listen(CardEnteredColumnEvent e) {
        EditCardBelongsColumnUseCase editCardBelongsColumnUseCase = new EditCardBelongsColumnUseCase(this.eventBus, this.cardRepository);
        EditCardBelongsColumnUseCaseInput input = new EditCardBelongsColumnUseCaseInput();

        input.setCardID(e.getCardID());
        input.setColumnID(e.getColumnID());

        editCardBelongsColumnUseCase.execute(input, new EditCardBelongsColumnUseCaseOutput());
    }
}
