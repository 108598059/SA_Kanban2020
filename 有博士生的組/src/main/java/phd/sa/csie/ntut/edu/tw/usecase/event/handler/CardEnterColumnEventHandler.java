package phd.sa.csie.ntut.edu.tw.usecase.event.handler;

import com.google.common.eventbus.Subscribe;
import phd.sa.csie.ntut.edu.tw.model.board.event.CardEnterColumnEvent;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.EditCardColumnUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.EditCardColumnUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.EditCardColumnUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class CardEnterColumnEventHandler implements DomainEventHandler<CardEnterColumnEvent> {
    private CardRepository cardRepository;

    public CardEnterColumnEventHandler(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Subscribe
    @Override
    public void listen(CardEnterColumnEvent e) {
        EditCardColumnUseCaseInput input = new EditCardColumnUseCaseInput();

        input.setCardID(e.getCardID());
        input.setColumnID(e.getColumnID());

        EditCardColumnUseCase editCardColumnUsecase = new EditCardColumnUseCase(this.cardRepository);
        editCardColumnUsecase.execute(input, new EditCardColumnUseCaseOutput());
    }
}
