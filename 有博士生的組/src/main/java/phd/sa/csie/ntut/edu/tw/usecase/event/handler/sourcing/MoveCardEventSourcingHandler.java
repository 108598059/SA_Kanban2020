package phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing;

import com.google.common.eventbus.Subscribe;
import phd.sa.csie.ntut.edu.tw.model.board.event.CardEnteredColumnEvent;
import phd.sa.csie.ntut.edu.tw.model.board.event.CardLeftColumnEvent;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.dto.CardEnteredColumnEventDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.dto.CardLeftColumnEventDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardEnteredColumnEventRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardLeftColumnEventRepository;

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
