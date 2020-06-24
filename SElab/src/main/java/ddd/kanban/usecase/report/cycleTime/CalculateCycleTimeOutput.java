package ddd.kanban.usecase.report.cycleTime;

import ddd.kanban.domain.model.report.cycletimecalculatorservice.CycleTime;

public class CalculateCycleTimeOutput {
    CycleTime cycleTime;

    public void setCycleTime(CycleTime cycleTime) {
        this.cycleTime = cycleTime;
    }

    public CycleTime getCycleTime() {
        return cycleTime;
    }
}
