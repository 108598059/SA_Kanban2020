package phd.sa.csie.ntut.edu.tw.controller.repository.memory;

import com.google.common.eventbus.Subscribe;
import phd.sa.csie.ntut.edu.tw.domain.model.DomainEvent;
import phd.sa.csie.ntut.edu.tw.usecase.repository.EventLogRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MemoryEventLogRepository implements EventLogRepository {
    private List<DomainEvent> eventList;

    public MemoryEventLogRepository() {
        this.eventList = new ArrayList<>();
    }

    @Override
    @Subscribe
    public void save(DomainEvent e) {
        this.eventList.add(e);
    }

    @Override
    public int size() {
        return this.eventList.size();
    }

    @Override
    public List<DomainEvent> getAll() {
        return this.eventList;
    }
}
