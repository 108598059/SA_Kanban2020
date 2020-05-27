package phd.sa.csie.ntut.edu.tw.usecase.card.calculate.leadtime;

import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.calculate.cycletime.CalculateCycleTimeUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.calculate.cycletime.CalculateCycleTimeUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.calculate.cycletime.CalculateCycleTimeUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.calculate.cycletime.CycleTime;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.event.CardEnteredColumnEventRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.event.CardLeftColumnEventRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.card.CardRepository;

import java.util.List;

public class CalculateLeadTimeUseCase extends UseCase<CalculateLeadTimeUseCaseInput, CalculateLeadTimeUseCaseOutput> {
    private CardEnteredColumnEventRepository cardEnteredColumnEventRepository;
    private CardLeftColumnEventRepository cardLeftColumnEventRepository;
    private CardRepository cardRepository;

    public CalculateLeadTimeUseCase(DomainEventBus eventBus,
                                    CardEnteredColumnEventRepository cardEnteredColumnEventRepository,
                                    CardLeftColumnEventRepository cardLeftColumnEventRepository,
                                    CardRepository cardRepository) {
        super(eventBus);
        this.cardEnteredColumnEventRepository = cardEnteredColumnEventRepository;
        this.cardLeftColumnEventRepository = cardLeftColumnEventRepository;
        this.cardRepository = cardRepository;
    }

    public void execute(CalculateLeadTimeUseCaseInput input, CalculateLeadTimeUseCaseOutput output) {
        CalculateCycleTimeUseCase calculateCycleTimeUseCase = new CalculateCycleTimeUseCase(this.eventBus,
                this.cardEnteredColumnEventRepository, this.cardLeftColumnEventRepository);
        CalculateCycleTimeUseCaseInput calculateCycleTimeUseCaseInput = new CalculateCycleTimeUseCaseInput();
        CalculateCycleTimeUseCaseOutput calculateCycleTimeUseCaseOutput = new CalculateCycleTimeUseCaseOutput();

        calculateCycleTimeUseCaseInput.setCardID(input.getCardID());

        calculateCycleTimeUseCase.execute(calculateCycleTimeUseCaseInput, calculateCycleTimeUseCaseOutput);

        List<CycleTime> cycleTimes = calculateCycleTimeUseCaseOutput.getCycleTimeList();

        long leadTime = 0;
        for (CycleTime cycleTime: cycleTimes) {
            leadTime += cycleTime.getTime();
        }

        Card card = CardDTOConverter.toEntity(this.cardRepository.findByID(input.getCardID()));
        card.setLeadTime(leadTime);

        this.cardRepository.save(CardDTOConverter.toDTO(card));
        this.eventBus.postAll(card);

        output.setLeadTime(leadTime);
    }
}
