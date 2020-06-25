package tw.teddysoft.clean.domain.model.kanbanboard.workflow;

public class LaneBuilder {

    private String name;
    private String workflowId;
    private LaneOrientation orientation = LaneOrientation.VERTICAL;
    private boolean isStage = true;
    private int wipLimit = 0;

    private LaneBuilder(){};

    public static LaneBuilder getInstance(){
        return new LaneBuilder();
    }

    public LaneBuilder name(String name) {
        this.name = name;
        return this;
    }

    public LaneBuilder wipLimit(int wipLimit) {
        this.wipLimit = wipLimit;
        return this;
    }

    public LaneBuilder workflowId(String workflowId) {
        this.workflowId = workflowId;
        return this;
    }

    public LaneBuilder swimlane() {
        isStage = false;
        orientation = LaneOrientation.HORIZONTAL;
        return this;
    }

    public LaneBuilder stage(){
        isStage = true;
        orientation = LaneOrientation.VERTICAL;
        return this;
    }

    public Lane build(){
        if (isStage)
            return new Stage(name,workflowId, wipLimit);
        else
            return new Swimlane(name,workflowId, wipLimit);
    }

    private boolean isVertical(){
        return LaneOrientation.VERTICAL == orientation ? true : false;
    }
}
