package phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.move;

import com.google.common.eventbus.Subscribe;
import phd.sa.csie.ntut.edu.tw.model.board.event.move.CardEnteredColumnEvent;
import phd.sa.csie.ntut.edu.tw.model.board.event.move.CardLeftColumnEvent;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.move.dto.entered.CardEnteredColumnEventDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.move.dto.left.CardLeftColumnEventDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.event.CardEnteredColumnEventRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.event.CardLeftColumnEventRepository;

public class MoveCardEventSourcingHandler {
    private CardEnteredColumnEventRepository cardEnteredColumnEventRepository;
    private CardLeftColumnEventRepository cardLeftColumnEventRepository;

    public MoveCardEventSourcingHandler(CardEnteredColumnEventRepository cardEnteredColumnEventRepository, CardLeftColumnEventRepository cardLeftColumnEventRepository) {
        this.cardEnteredColumnEventRepository = cardEnteredColumnEventRepository;
        this.cardLeftColumnEventRepository = cardLeftColumnEventRepository;
    }

    @Subscribe
    public void handleEnteredColumnEvent(CardEnteredColumnEvent e) {
        this.cardEnteredColumnEventRepository.save(CardEnteredColumnEventDTOConverter.toDTO(e));
    }

    @Subscribe
    public void handleLeftColumnEvent(CardLeftColumnEvent e) {
        this.cardLeftColumnEventRepository.save(CardLeftColumnEventDTOConverter.toDTO(e));
    }
}
