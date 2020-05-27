package phd.sa.csie.ntut.edu.tw.usecase.card.calculate.leadtime;

import phd.sa.csie.ntut.edu.tw.usecase.UseCaseOutput;

public class CalculateLeadTimeUseCaseOutput implements UseCaseOutput {
    private long leadTime;

    public long getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(long leadTime) {
        this.leadTime = leadTime;
    }
}
