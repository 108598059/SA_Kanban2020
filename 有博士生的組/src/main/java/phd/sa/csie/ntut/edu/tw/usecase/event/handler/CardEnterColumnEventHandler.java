package phd.sa.csie.ntut.edu.tw.usecase.event.handler;

import com.google.common.eventbus.Subscribe;
import phd.sa.csie.ntut.edu.tw.model.board.event.CardEnterColumnEvent;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.EditCardColumnInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.EditCardColumnOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.EditCardColumnUsecase;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class CardEnterColumnEventHandler implements DomainEventHandler<CardEnterColumnEvent> {
    private CardRepository cardRepository;

    public CardEnterColumnEventHandler(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Subscribe
    @Override
    public void listen(CardEnterColumnEvent e) {
        EditCardColumnInput input = new EditCardColumnInput();

        input.setCardID(e.getCardID());
        input.setColumnID(e.getColumnID());

        EditCardColumnUsecase editCardColumnUsecase = new EditCardColumnUsecase(this.cardRepository);
        editCardColumnUsecase.execute(input, new EditCardColumnOutput());
    }
}
