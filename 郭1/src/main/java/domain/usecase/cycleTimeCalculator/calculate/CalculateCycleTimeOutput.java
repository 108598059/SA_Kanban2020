package domain.usecase.cycleTimeCalculator.calculate;

import domain.usecase.cycleTimeCalculator.CycleTime;

public interface CalculateCycleTimeOutput {
    public void setCycleTime(CycleTime cycleTime);
    public CycleTime getCycleTime();
}
