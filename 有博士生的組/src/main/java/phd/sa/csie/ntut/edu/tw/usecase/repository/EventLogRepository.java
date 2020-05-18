package phd.sa.csie.ntut.edu.tw.usecase.repository;

import phd.sa.csie.ntut.edu.tw.usecase.event.handler.dto.DomainEventDTO;

import java.util.List;

public interface EventLogRepository {
    void save(DomainEventDTO e);

    int size();

    List<DomainEventDTO> getAll();
}
