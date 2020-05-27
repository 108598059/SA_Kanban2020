package phd.sa.csie.ntut.edu.tw.usecase.card.calculate.cycletime;

import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.move.dto.entered.CardEnteredColumnEventDTO;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.move.dto.left.CardLeftColumnEventDTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.event.CardEnteredColumnEventRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.event.CardLeftColumnEventRepository;

import java.util.ArrayList;
import java.util.List;

public class CalculateCycleTimeUseCase extends UseCase<CalculateCycleTimeUseCaseInput, CalculateCycleTimeUseCaseOutput> {
    private CardEnteredColumnEventRepository cardEnteredColumnEventRepository;
    private CardLeftColumnEventRepository cardLeftColumnEventRepository;

    public CalculateCycleTimeUseCase(DomainEventBus eventBus, CardEnteredColumnEventRepository cardEnteredColumnEventRepository, CardLeftColumnEventRepository cardLeftColumnEventRepository) {
        super(eventBus);
        this.cardEnteredColumnEventRepository = cardEnteredColumnEventRepository;
        this.cardLeftColumnEventRepository = cardLeftColumnEventRepository;
    }

    public void execute(CalculateCycleTimeUseCaseInput input, CalculateCycleTimeUseCaseOutput output) {
        List<CardEnteredColumnEventDTO> enteredEvents = this.cardEnteredColumnEventRepository.findByCardID(input.getCardID());
        List<CardLeftColumnEventDTO> leftEvents = this.cardLeftColumnEventRepository.findByCardID(input.getCardID());

        List<CycleTime> result = new ArrayList<>();

        for (CardEnteredColumnEventDTO enteredEvent: enteredEvents) {
            for (CardLeftColumnEventDTO leftEvent: leftEvents) {
                if (enteredEvent.getColumnID().equals(leftEvent.getColumnID())) {
                    result.add( new CycleTime(enteredEvent.getCardID(),
                                              enteredEvent.getColumnID(),
                                              enteredEvent.getOccurredTime(),
                                              leftEvent.getOccurredTime()));
                }
            }
        }

        output.setCycleTimeList(result);
    }
}
