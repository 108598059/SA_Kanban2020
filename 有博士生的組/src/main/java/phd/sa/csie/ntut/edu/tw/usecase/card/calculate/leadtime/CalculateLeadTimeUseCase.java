package phd.sa.csie.ntut.edu.tw.usecase.card.calculate.leadtime;

import phd.sa.csie.ntut.edu.tw.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.repository.EventLogRepository;

import java.util.List;

public class CalculateLeadTimeUseCase extends UseCase<CalculateLeadTimeuseCaseInput, CalculateLeadTimeUseCaseOutput> {

    private EventLogRepository eventLogRepository;

    public CalculateLeadTimeUseCase(DomainEventBus eventBus, EventLogRepository eventLogRepository){
        super(eventBus);
        this.eventLogRepository = eventLogRepository;
    }

    public void execute(CalculateLeadTimeuseCaseInput input, CalculateLeadTimeUseCaseOutput output) {
//        List<DomainEventDTO> eventList = this.eventLogRepository.findAllBySourceID(input.getBoardID());
//
//        for (DomainEventDTO event : eventList) {
//            if (event.getSourceName().contains(CardEnteredColumnEvent.EVENT_NAME) &&
//                event.getSourceName().contains(input.getCardID())) {
//                cardEnteredEventList.
//            }
//
//        }


    }
}
