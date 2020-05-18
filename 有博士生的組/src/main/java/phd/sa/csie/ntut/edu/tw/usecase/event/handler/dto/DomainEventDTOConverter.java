package phd.sa.csie.ntut.edu.tw.usecase.event.handler.dto;

import phd.sa.csie.ntut.edu.tw.model.DomainEvent;

public class DomainEventDTOConverter {
    public static DomainEventDTO toDTO(DomainEvent e) {
        DomainEventDTO domainEventDTO = new DomainEventDTO();
        domainEventDTO.setEventVersion(e.eventVersion());
        domainEventDTO.setOccurredTime(e.occurredOn());
        domainEventDTO.setDetail(e.detail());
        domainEventDTO.setSourceID(e.getSourceId());
        domainEventDTO.setSourceName(e.getSourceName());
        return domainEventDTO;
    }
}
