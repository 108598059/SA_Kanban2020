package domain.model.aggregate.workflow.valueObject.cycleTime;

public class CycleTimeInLane {
    private String laneId;
    private long diff;

    public CycleTimeInLane(String laneId, long diff) {
        this.laneId = laneId;
        this.diff = diff;
    }

    public String getLaneId() {
        return laneId;
    }

    public long getDiff() {
        return diff;
    }
}
