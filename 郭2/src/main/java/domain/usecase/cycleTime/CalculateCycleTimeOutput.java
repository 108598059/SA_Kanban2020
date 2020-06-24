package domain.usecase.cycleTime;

public class CalculateCycleTimeOutput {
    private long diffDays;
    private long diffHours;
    private long diffMinutes;
    private long diffSeconds;

    public void setDiffDays(long diffDays) {
        this.diffDays = diffDays;
    }

    public long getDiffDays() {
        return diffDays;
    }

    public void setDiffHours(long diffHours) {
        this.diffHours = diffHours;
    }

    public long getDiffHours() {
        return diffHours;
    }

    public void setDiffMinutes(long diffMinutes) {
        this.diffMinutes = diffMinutes;
    }

    public long getDiffMinutes() {
        return diffMinutes;
    }

    public void setDiffSeconds(long diffSeconds) {
        this.diffSeconds = diffSeconds;
    }

    public long getDiffSeconds() {
        return diffSeconds;
    }
}