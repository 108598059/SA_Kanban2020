package phd.sa.csie.ntut.edu.tw.usecase.cycletime;

import phd.sa.csie.ntut.edu.tw.model.aggregate.board.event.move.CardEnteredColumnEvent;
import phd.sa.csie.ntut.edu.tw.model.aggregate.board.event.move.CardLeftColumnEvent;
import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.service.cycletimecalculator.CycleTime;
import phd.sa.csie.ntut.edu.tw.model.service.cycletimecalculator.CycleTimeCalculator;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.move.dto.entered.CardEnteredColumnEventDTO;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.move.dto.entered.CardEnteredColumnEventDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.move.dto.left.CardLeftColumnEventDTO;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.move.dto.left.CardLeftColumnEventDTOConverter;
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
        List<CardEnteredColumnEventDTO> enteredEventDTOList = this.cardEnteredColumnEventRepository
                .findByCardIDInPeriod(input.getCardID(), input.getStartTime(), input.getEndTime());
        List<CardLeftColumnEventDTO> leftEventDTOList = this.cardLeftColumnEventRepository
                .findByCardIDInPeriod(input.getCardID(), input.getStartTime(), input.getEndTime());

        List<CardEnteredColumnEvent> enteredEventList = new ArrayList<>();
        List<CardLeftColumnEvent> leftEventList = new ArrayList<>();

        for (CardEnteredColumnEventDTO enteredEventDTO: enteredEventDTOList) {
            enteredEventList.add(CardEnteredColumnEventDTOConverter.toEntity(enteredEventDTO));
        }

        for (CardLeftColumnEventDTO leftEventDTO: leftEventDTOList) {
            leftEventList.add(CardLeftColumnEventDTOConverter.toEntity(leftEventDTO));
        }

        CycleTimeCalculator cycleTimeCalculator = new CycleTimeCalculator();
        CycleTime cycleTime = cycleTimeCalculator.process(input.getCardID(),
                                                        input.getStartColumnID(), input.getEndColumnID(),
                                                        input.getStartTime(), input.getEndTime(),
                                                        enteredEventList, leftEventList);

        this.eventBus.postAll(cycleTimeCalculator);
        output.setCardID(input.getCardID());
        output.setStartColumnID(input.getStartColumnID());
        output.setEndColumnID(input.getEndColumnID());
        output.setCycleTime(cycleTime.getTime());
    }
}
