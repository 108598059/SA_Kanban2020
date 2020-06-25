package phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.domain;

import com.google.common.eventbus.Subscribe;
import phd.sa.csie.ntut.edu.tw.model.domain.DomainEvent;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.DomainEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.domain.dto.DomainEventDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.event.EventLogRepository;

public class EventSourcingHandler implements DomainEventHandler<DomainEvent> {
    private EventLogRepository eventLogRepository;

    public EventSourcingHandler(EventLogRepository eventLogRepository) {
        this.eventLogRepository = eventLogRepository;
    }

    @Subscribe
    @Override
    public void listen(DomainEvent domainEvent) {
        this.eventLogRepository.save(DomainEventDTOConverter.toDTO(domainEvent));
    }
}
