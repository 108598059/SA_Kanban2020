package phd.sa.csie.ntut.edu.tw.usecase;

import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.usecase.dto.DTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.IRepository;

public abstract class UseCase<R extends IRepository<?>, C extends DTOConverter<?>, I extends UseCaseInput, O extends UseCaseOutput> {
    protected R repository;
    protected C dtoConverter;
    protected DomainEventBus eventBus;

    public UseCase(R repository, DomainEventBus eventBus, C dtoConverter) {
        this.repository = repository;
        this.eventBus = eventBus;
        this.dtoConverter = dtoConverter;
    }

    public abstract void execute(I input, O output);
}