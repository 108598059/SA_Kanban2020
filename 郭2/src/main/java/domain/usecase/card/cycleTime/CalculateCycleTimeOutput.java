package domain.usecase.card.cycleTime;

import domain.model.aggregate.workflow.valueObject.cycleTime.CycleTime;

public class CalculateCycleTimeOutput {
    private CycleTime cycleTime;

    public void setCycleTime(CycleTime cycleTime) {
        this.cycleTime = cycleTime;
    }

    public CycleTime getCycleTime() {
        return cycleTime;
    }
}
