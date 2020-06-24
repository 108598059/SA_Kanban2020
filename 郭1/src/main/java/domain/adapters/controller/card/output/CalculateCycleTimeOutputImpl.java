package domain.adapters.controller.card.output;

import domain.usecase.cycleTimeCalculator.calculate.CalculateCycleTimeOutput;
import domain.usecase.cycleTimeCalculator.CycleTime;

public class CalculateCycleTimeOutputImpl implements CalculateCycleTimeOutput {
    private CycleTime cycleTime;

    public void setCycleTime(CycleTime cycleTime) {
        this.cycleTime = cycleTime;
    }

    public CycleTime getCycleTime() {
        return cycleTime;
    }
}
