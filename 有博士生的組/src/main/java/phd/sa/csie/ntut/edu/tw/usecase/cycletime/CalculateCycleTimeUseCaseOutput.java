package phd.sa.csie.ntut.edu.tw.usecase.cycletime;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;

public class CalculateCycleTimeUseCaseOutput implements UseCaseOutput {
    private CycleTime cycleTime;

    public CycleTime getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(CycleTime cycleTime) {
        this.cycleTime = cycleTime;
    }
}
