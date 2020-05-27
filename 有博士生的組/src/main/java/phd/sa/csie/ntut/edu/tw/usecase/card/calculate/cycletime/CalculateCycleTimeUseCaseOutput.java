package phd.sa.csie.ntut.edu.tw.usecase.card.calculate.cycletime;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;

import java.util.List;

public class CalculateCycleTimeUseCaseOutput implements UseCaseOutput {
    List<CycleTime> cycleTimeList;

    public List<CycleTime> getCycleTimeList() {
        return cycleTimeList;
    }

    public void setCycleTimeList(List<CycleTime> cycleTimeList) {
        this.cycleTimeList = cycleTimeList;
    }
}
