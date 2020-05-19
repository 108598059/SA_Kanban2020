package phd.sa.csie.ntut.edu.tw.usecase.event.handler;

import com.google.common.eventbus.Subscribe;
import phd.sa.csie.ntut.edu.tw.model.DomainEvent;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.dto.DomainEventDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.EventLogRepository;

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
