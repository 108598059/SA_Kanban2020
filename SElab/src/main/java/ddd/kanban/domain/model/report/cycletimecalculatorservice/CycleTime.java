package ddd.kanban.domain.model.report.cycletimecalculatorservice;

public class CycleTime {
    private long millisecond = 0;

    public CycleTime(long millisecond) {
        this.millisecond = millisecond;
    }

    public long getMillisecond() {
        return millisecond;
    }

    public int getDay(){
        return (int) (millisecond / 1000 / 60 / 60 / 24);
    }

}
