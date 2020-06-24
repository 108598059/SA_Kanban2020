package ddd.kanban.domain.model.report.cycletimecalculatorservice.event;

import ddd.kanban.domain.model.AbstractDomainEvent;
import ddd.kanban.domain.model.report.cycletimecalculatorservice.CycleTime;

import java.util.List;

public class CycleTimeCalculated extends AbstractDomainEvent {

    private List<String> columnIntervalIds;
    private CycleTime cycleTime;

    public CycleTimeCalculated(String cardId, String cardName, List<String> columnIntervalIds, CycleTime cycleTime, String id){
        super(cardId, id);
        this.columnIntervalIds = columnIntervalIds;
        this.cycleTime = cycleTime;
    }

    public List<String> getColumnIntervalIds() {
        return columnIntervalIds;
    }

    public CycleTime getCycleTime() {
        return cycleTime;
    }
}
