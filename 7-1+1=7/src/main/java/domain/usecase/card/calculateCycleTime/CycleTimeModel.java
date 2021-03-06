package domain.usecase.card.calculateCycleTime;

public class CycleTimeModel {
    private long diffDays;
    private long diffHours;
    private long diffMinutes;
    private long diffSeconds;

    public CycleTimeModel(long diff) {
        diffDays = diff/(24*3600);
        diffHours = diff %(24*3600)/3600;
        diffMinutes = diff % 3600/60;
        diffSeconds = diff % 60;
    }

    public long getDiffDays() {
        return diffDays;
    }

    public long getDiffHours() {
        return diffHours;
    }

    public long getDiffMinutes() {
        return diffMinutes;
    }

    public long getDiffSeconds() {
        return diffSeconds;
    }
}
