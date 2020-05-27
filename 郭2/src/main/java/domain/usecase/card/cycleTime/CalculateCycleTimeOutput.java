package domain.usecase.card.cycleTime;

import domain.model.valueObject.CycleTime;

public class CalculateCycleTimeOutput {
    private CycleTime cycleTime;

    public void setCycleTime(CycleTime cycleTime) {
        this.cycleTime = cycleTime;
    }

    public CycleTime getCycleTime() {
        return cycleTime;
    }
}
