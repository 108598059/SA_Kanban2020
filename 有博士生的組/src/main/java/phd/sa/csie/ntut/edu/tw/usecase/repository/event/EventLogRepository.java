package phd.sa.csie.ntut.edu.tw.usecase.repository.event;

import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.domain.dto.DomainEventDTO;

import java.util.List;

public interface EventLogRepository {
    void save(DomainEventDTO e);

    int size();

    List<DomainEventDTO> getAll();
}
