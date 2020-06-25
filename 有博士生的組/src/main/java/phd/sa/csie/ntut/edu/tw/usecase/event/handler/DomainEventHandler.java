package phd.sa.csie.ntut.edu.tw.usecase.event.handler;

import phd.sa.csie.ntut.edu.tw.model.domain.DomainEvent;

public interface DomainEventHandler<E extends DomainEvent> {
    void listen(E e);
}
