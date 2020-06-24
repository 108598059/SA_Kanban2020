package phd.sa.csie.ntut.edu.tw.usecase.cycletime;

import phd.sa.csie.ntut.edu.tw.model.board.event.move.CardEnteredColumnEvent;
import phd.sa.csie.ntut.edu.tw.model.board.event.move.CardLeftColumnEvent;
import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.move.dto.entered.CardEnteredColumnEventDTO;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.move.dto.entered.CardEnteredColumnEventDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.move.dto.left.CardLeftColumnEventDTO;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.move.dto.left.CardLeftColumnEventDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.event.CardEnteredColumnEventRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.event.CardLeftColumnEventRepository;

import java.util.Date;
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

        Date startTime = new Date();
        Date endTime = new Date(0);

        for (CardEnteredColumnEventDTO enteredEventDTO: enteredEvents) {
            CardEnteredColumnEvent enteredEvent = CardEnteredColumnEventDTOConverter.toEntity(enteredEventDTO);
            if (enteredEvent.getCardID().equals(input.getCardID()) &&
                enteredEvent.getToColumnID().equals(input.getStartColumnID())) {
                Date timeGot = enteredEvent.occurredOn();
                if (startTime.compareTo(timeGot) > 0) {
                    startTime = timeGot;
                }
            }
        }

        for (CardLeftColumnEventDTO enterEventDTO: leftEvents) {
            CardLeftColumnEvent leftEvent = CardLeftColumnEventDTOConverter.toEntity(enterEventDTO);

            if (leftEvent.getCardID().equals(input.getCardID()) &&
                leftEvent.getColumnID().equals(input.getEndColumnID())) {
                Date timeGot = leftEvent.occurredOn();

                if (timeGot.compareTo(endTime) > 0) {
                    endTime = timeGot;
                }
            }
        }

        CycleTime cycleTime = new CycleTime(input.getCardID(), input.getStartColumnID(), input.getEndColumnID(), startTime, endTime);

        output.setCycleTime(cycleTime);
    }
}
