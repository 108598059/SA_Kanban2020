package phd.sa.csie.ntut.edu.tw.adapter.repository.memory.event;

import java.util.ArrayList;
import java.util.List;

import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.domain.dto.DomainEventDTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.event.EventLogRepository;

public class MemoryEventLogRepository implements EventLogRepository {
    private List<DomainEventDTO> eventList;

    public MemoryEventLogRepository() {
        this.eventList = new ArrayList<>();
    }

    @Override
    public void save(DomainEventDTO e) {
        this.eventList.add(e);
    }

    @Override
    public int size() {
        return this.eventList.size();
    }

    @Override
    public List<DomainEventDTO> getAll() {
        return this.eventList;
    }
}
