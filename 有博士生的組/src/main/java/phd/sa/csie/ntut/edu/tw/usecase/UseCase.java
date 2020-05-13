package phd.sa.csie.ntut.edu.tw.usecase;

import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.usecase.dto.DTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.IRepository;

public abstract class UseCase<I extends UseCaseInput, O extends UseCaseOutput> {
    protected DomainEventBus eventBus;

    public UseCase(DomainEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public abstract void execute(I input, O output);
}