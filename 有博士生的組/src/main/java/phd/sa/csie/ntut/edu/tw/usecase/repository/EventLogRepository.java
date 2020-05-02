package phd.sa.csie.ntut.edu.tw.usecase.repository;

import phd.sa.csie.ntut.edu.tw.domain.model.DomainEvent;

import java.util.List;

public interface EventLogRepository {
    void save(DomainEvent e);
    int size();
    List<DomainEvent> getAll();
}
