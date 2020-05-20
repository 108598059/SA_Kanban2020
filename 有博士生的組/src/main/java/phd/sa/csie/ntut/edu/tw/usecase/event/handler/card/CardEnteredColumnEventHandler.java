package phd.sa.csie.ntut.edu.tw.usecase.event.handler.card;

import com.google.common.eventbus.Subscribe;
import phd.sa.csie.ntut.edu.tw.model.board.event.CardEnteredColumnEvent;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.column.EditCardColumnUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.column.EditCardColumnUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.column.EditCardColumnUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.DomainEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class CardEnteredColumnEventHandler implements DomainEventHandler<CardEnteredColumnEvent> {
    private CardRepository cardRepository;

    public CardEnteredColumnEventHandler(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Subscribe
    @Override
    public void listen(CardEnteredColumnEvent e) {
        EditCardColumnUseCase editCardColumnUseCase = new EditCardColumnUseCase(this.cardRepository);
        EditCardColumnUseCaseInput input = new EditCardColumnUseCaseInput();

        input.setCardID(e.getCardID());
        input.setColumnID(e.getColumnID());

        editCardColumnUseCase.execute(input, new EditCardColumnUseCaseOutput());
    }
}
