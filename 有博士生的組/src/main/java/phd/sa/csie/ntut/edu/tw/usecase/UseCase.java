package phd.sa.csie.ntut.edu.tw.usecase;

import phd.sa.csie.ntut.edu.tw.model.DomainEventBus;

public abstract class UseCase<I extends UseCaseInput, O extends UseCaseOutput> {
    protected DomainEventBus eventBus;
    public UseCase() {}

    public UseCase(DomainEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public abstract void execute(I input, O output);
}