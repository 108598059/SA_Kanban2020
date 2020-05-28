package domain.adapters.controller.card;

import domain.usecase.card.cycletime.CalculateCycleTimeOutput;
import domain.usecase.card.cycletime.CycleTime;

public class CalculateCycleTimeOutputImpl implements CalculateCycleTimeOutput {
    private CycleTime cycleTime;

    public void setCycleTime(CycleTime cycleTime) {
        this.cycleTime = cycleTime;
    }

    public CycleTime getCycleTime() {
        return cycleTime;
    }
}
