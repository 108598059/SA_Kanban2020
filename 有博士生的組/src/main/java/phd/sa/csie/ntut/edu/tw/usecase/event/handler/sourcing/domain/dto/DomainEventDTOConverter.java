package phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.domain.dto;

import phd.sa.csie.ntut.edu.tw.model.domain.DomainEvent;

public class DomainEventDTOConverter {
    public static DomainEventDTO toDTO(DomainEvent e) {
        DomainEventDTO domainEventDTO = new DomainEventDTO();

        domainEventDTO.setEventVersion(e.eventVersion());
        domainEventDTO.setOccurredTime(e.occurredOn());
        domainEventDTO.setDetail(e.detail());
        domainEventDTO.setSourceID(e.getSourceID());
        domainEventDTO.setSourceName(e.getSourceName());

        return domainEventDTO;
    }
}
